package tech.dlii.opencomputers.server.machine;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.machine.*;
import tech.dlii.opencomputers.api.machine.architecture.Architecture;
import tech.dlii.opencomputers.api.machine.architecture.Callback;
import tech.dlii.opencomputers.api.machine.architecture.ExecutionResult;
import tech.dlii.opencomputers.api.machine.architecture.Value;
import tech.dlii.opencomputers.api.network.Visibility;
import tech.dlii.opencomputers.api.network.node.ComponentNode;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.api.network.prefab.AbstractManagedEnvironment;
import tech.dlii.opencomputers.common.config.Configuration;
import tech.dlii.opencomputers.server.machine.architecture.luaj.LuaJLuaArchitecture;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Machine extends AbstractManagedEnvironment implements Runnable, tech.dlii.opencomputers.api.machine.Machine {

    private Architecture architecture = null;
    private MachineHost host;

    private Queue<MachineSignal> signals = new ArrayDeque<>();
    private Stack<MachineState> state = new Stack<>();
    private double maxCallBudget = 1.0;
    private int maxSignalQueueSize = Configuration.MAX_SIGNAL_QUEUE_SIZE;
    private double callBudget = 1.0;
    private long worldTime = 0L;
    private long upTime = 0L;
    private long cpuStartTime = 0L;
    private long cpuTotalTime = 0L;
    private long remainingIdleTicks = 0L;
    private long remainingPauseTicks = 0L;
    boolean isSynchronizedCall = false;
    private @Nullable String message = null;

    public Machine(MachineHost host) {
        this.host = host;
        setNode(API.network.newNode(this, Visibility.Network).build());
        state.push(MachineState.Stopped);
        OpenComputers.LOGGER.info("Created machine");
    }

    @Override
    public boolean start() {
        if (!init()) {
            OpenComputers.LOGGER.warn("Start failure: Machine not initialized.");
            return false;
        }
        // Initial checks
        switchTo(MachineState.Starting);
        upTime = 0;
        // Send node event
        return true;
    }

    public boolean init() {
        if (architecture == null) {
            architecture = new LuaJLuaArchitecture(this);
//            return false;
        }
        message = null;
        signals.clear();

        // Connect /tmp

        try {
            OpenComputers.LOGGER.info("Initializing machine");
            return architecture.initialize();
        }
        catch (Throwable exception){
            OpenComputers.LOGGER.warn("Failed initializing computer.", exception);
            close();
        }
        return false;
    }

    public void tick() {
        worldTime = host.level().getDayTime();
        upTime++;
        if (remainingIdleTicks > 0) {
            remainingIdleTicks--;
        }

        callBudget = maxCallBudget;

        synchronized (state) {
            switch (state.peek()) {
                // Booting up.
                case Starting:
                    switchTo(MachineState.Yielded);
                    break;
                case Restarting:
                    close();
                    // @TODO erase tmp on reboot
                    start();
                    break;
                case Sleeping:
                    if (remainingIdleTicks <= 0 || signals.size() > 0) {
                        switchTo(MachineState.Yielded);
                    }
                    break;
                case Paused:
                    if (remainingPauseTicks > 0) {
                        remainingPauseTicks--;
                    }
                    else {
                        // @TODO Verify components here
                        state.pop();
                        switchTo(state.peek());
                    }
                    break;
                case SynchronizedCall:
                    switchTo(MachineState.Running);
                    try {
                        isSynchronizedCall = true;
                        architecture.run();
                        isSynchronizedCall = false;
                        if (state.peek() == MachineState.Running) {
                            switchTo(MachineState.SynchronizedReturn);
                        }
                        else if (state.peek() == MachineState.Paused) {
                            state.pop(); // Paused
                            state.pop(); // Running, no switchTo to avoid new future.
                            state.push(MachineState.SynchronizedReturn);
                            state.push(MachineState.Paused);
                        }
                        else if (state.peek() == MachineState.Stopping) {
                            state.clear();
                            state.push(MachineState.Stopped);
                        }
                        else {
                            throw new AssertionError();
                        }
                    }
                    catch (Throwable t) {
                        if (t instanceof Error e && e.getMessage().equals("not enough memory")) {
                            crash("gui.error.out_of_memory");
                        } else {
                            OpenComputers.LOGGER.warn("Faulty architecture implementation for synchronized calls.", t);
                            crash("gui.error.internal_error");
                        }
                    }
                    finally {
                        isSynchronizedCall = false;
                    }
                    break;
            }

            if (state.peek() == MachineState.Stopping) {
                synchronized (this) {
                    tryClose();
                }
            }
        }

    }

    @Override
    public void run() {
        synchronized (this) {
            boolean isSynchronizedReturn;
            synchronized (state) {
                if (state.peek() != MachineState.Yielded && state.peek() != MachineState.SynchronizedReturn) {
                    return;
                }
                // If the game is paused, we also pause
                if (isGamePaused()) {
                    state.push(MachineState.Paused);
                    return;
                }
                isSynchronizedReturn = switchTo(MachineState.Running) == MachineState.SynchronizedReturn;
            }

            cpuStartTime = System.nanoTime();

            try {
                ExecutionResult result = architecture.runAsynchronous(isSynchronizedReturn);

                synchronized (state) {
                    switch (state.peek()) {
                        case Running:
                            switch (result) {
                                case ExecutionResult.Sleep sleep:
                                    synchronized (signals) {
                                        if (signals.size() > 0 && sleep.ticks > 0) {
                                            switchTo(MachineState.Sleeping);
                                            remainingIdleTicks = sleep.ticks;
                                        }
                                        else {
                                            switchTo(MachineState.Yielded);
                                        }
                                    }
                                    switchTo(MachineState.Sleeping);
                                    break;
                                case ExecutionResult.SynchronizedCall synchronizedCall:
                                    switchTo(MachineState.SynchronizedCall);
                                    break;
                                case ExecutionResult.Shutdown shutdown:
                                    switchTo(shutdown.reboot ? MachineState.Restarting : MachineState.Stopping);
                                    break;
                                case ExecutionResult.Error error:
//                                    beep("--");
                                    crash(Optional.of(error.message).orElse("unknown error"));
                                default:
                            }
                            break;
                        case Paused:
                            state.pop(); // Paused
                            state.pop(); // Running, no switchTo to avoid new future.
                            switch (result) {
                                case ExecutionResult.Sleep sleep:
                                    remainingIdleTicks = sleep.ticks;
                                    state.push(MachineState.Sleeping);
                                    break;
                                case ExecutionResult.SynchronizedCall synchronizedCall:
                                    state.push(MachineState.SynchronizedCall);
                                    break;
                                case ExecutionResult.Shutdown shutdown:
                                    state.push(shutdown.reboot ? MachineState.Restarting : MachineState.Stopping);
                                    break;
                                case ExecutionResult.Error error:
                                    crash(Optional.of(error.message).orElse("unknown error"));
                                default:
                            }
                            break;
                        case Stopping:
                            state.clear();
                            state.push(MachineState.Stopping);
                            break;
                        case Restarting:
                            break;
                        default:
                            throw new AssertionError("Invalid state in executor post-processing.");
                    }
                    assert !isExecuting();
                }
            } catch (Throwable throwable) {
                OpenComputers.LOGGER.warn("Architecture's runThreaded threw an error. This should never happen!", throwable);
                crash("gui.error.internal_error");
            }

            cpuTotalTime += System.nanoTime() - cpuStartTime;
        }
    }

    @Override
    public boolean pause(double seconds) {
        final int ticksToPause = Math.max((int) (seconds * 20), 0);

        // Helper: should we enter/extend pause given the current state?
        java.util.function.Predicate<MachineState> shouldPause = (s) -> switch (s) {
            case Stopping, Stopped -> false;
            case Paused -> ticksToPause > remainingPauseTicks;
            default -> true;
        };

        // We don't want to lock the machine if we can help it...
        // so test the state alone first
        MachineState current;
        synchronized (state) {
            current = state.peek();
        }
        if (!shouldPause.test(current)) {
            return false;
        }

        synchronized (Machine.this) {
            synchronized (state) {
                current = state.peek();
                if (!shouldPause.test(current)) {
                    return false;
                }
                if (current != MachineState.Paused) {
                    assert !state.contains(MachineState.Paused);
                    state.push(MachineState.Paused);
                }
                remainingPauseTicks = ticksToPause;
//                    host.markChanged();
            }
        }
        return true;
    }

    @Override
    public boolean stop() {
        synchronized (state) {
            MachineState currentState = state.peek();
            if (currentState == MachineState.Stopping || currentState == MachineState.Stopped) {
                return false;
            }
            state.push(MachineState.Stopping);
            // Schedule Close
        }
        return true;
    }

    @Override
    public boolean crash(String message) {
        this.message = message;
        synchronized (state) {
            boolean result = stop();
            if (state.peek() == MachineState.Stopping) {
                state.clear();
                state.push(MachineState.Stopping);
            }
            return result;
        }
    }

    public MachineState switchTo(MachineState newState) {
        MachineState oldState = state.pop();
        if (newState == MachineState.Stopping || newState == MachineState.Restarting) {
            state.clear();
        }
        state.push(newState);
        if (newState == MachineState.Yielded || newState == MachineState.SynchronizedReturn) {
            remainingIdleTicks = 0;
            Machines.threadPool().schedule(this, Configuration.EXECUTION_DELAY, TimeUnit.MILLISECONDS);
        }
//        host.markChanged();
        return oldState;
    }

    @Override
    public Map<String, Callback> methods(Object value) {
        return Map.of();
    }

    @Override
    public Object[] invoke(String address, String method, Object[] args) throws TickCallLimitReachedException, IllegalArgumentException, Exception {
        if (node() == null || node().network() == null) {
            // Not really, but makes the VM stop, which is what we want in this case,
            // because it means we've been disconnected / disposed already.
            throw new TickCallLimitReachedException();
        }
        Node otherNode = node().network().node(address);
        if (otherNode != node()) {
            if (!(otherNode instanceof ComponentNode componentNode) || !componentNode.canBeSeenFrom(node())) {
                throw new IllegalArgumentException("No such component");
            }
        }
        ComponentNode componentNode = (ComponentNode) otherNode;
        Callback annotation = componentNode.annotation(method);
        if (annotation.async()) {
            consumeCallBudget(1.0 / annotation.limit());
        }
        return componentNode.invoke(method, this, args);
    }

    @Override
    public Object[] invoke(Value value, String method, Object[] args) throws TickCallLimitReachedException, IllegalArgumentException, Exception {
        // Lookup callback via value
        return new Object[0];
    }

    @Override
    public @Nullable MachineSignal popSignal() {
        synchronized (signals) {
            return signals.poll(); // Dequeue, return null if empty
        }
    }

    @Override
    public void consumeCallBudget(double callCost) {

    }

    @Override
    public void onHostChanged() {

    }

    private boolean isGamePaused() {
        boolean paused = Minecraft.getInstance().isPaused();
        return paused;
    }

    public boolean tryClose() {
        if (isExecuting()) {
             return false;
        }
        close();
        // Force delete temp files
        // Notify nodes
        return true;
    }

    private void close() {
        synchronized (state) {
            if (!state.isEmpty() && state.peek() == MachineState.Stopped) {
                return;
            }
        }
        synchronized (this) {
            synchronized (state) {
                // Re-check under both locks to avoid TOCTOU races.
                if (!state.isEmpty() && state.peek() == MachineState.Stopped) {
                    return;
                }
                state.clear();
                state.push(MachineState.Stopped);

                if (architecture != null) {
                    architecture.close();
                }

                signals.clear();
                upTime = 0L;
                cpuTotalTime = 0L;
                cpuStartTime = 0L;
                remainingIdleTicks = 0L;
            }
        }
//        host.markChanged();
    }

    @Override
    public Map<String, String> components() {
        return Map.of();
    }

    @Override
    public int componentCount() {
        return 0;
    }

    @Override
    public int maxComponents() {
        return 0;
    }

    @Override
    public long worldTime() {
        return 0;
    }

    @Override
    public double upTime() {
        return upTime / 20.0;
    }

    @Override
    public double cpuTime() {
        return (cpuTotalTime + System.nanoTime() - cpuStartTime) * 10e-10;
    }

    @Override
    public boolean signal(String name, Object... args) {
        synchronized (state) {
            if (state.peek() == MachineState.Stopped || state.peek() == MachineState.Stopping) {
                return false;
            }
            synchronized (signals) {
                if (signals.size() >= maxSignalQueueSize) {
                    return false;
                }
                if (args == null) {
                    signals.add(new MachineSignal(name, new Object[0]));
                }
                else {
                    signals.add(new MachineSignal(name, args));
                }
            }
        }
        return true;
    }

    @Override
    public MachineHost host() {
        return this.host;
    }

    @Override
    public Architecture architecture() {
        return this.architecture;
    }

    @Override
    public @Nullable String lastError() {
        return this.message;
    }

    public boolean isExecuting() {
        synchronized (state) {
            return state.contains(MachineState.Running);
        }
    }

    @Override
    public boolean isRunning() {
        synchronized (state) {
            return state.peek() != MachineState.Stopped && state.peek() != MachineState.Paused;
        }
    }

    @Override
    public boolean isPaused() {
        synchronized (state) {
            return state.peek() == MachineState.Paused && remainingPauseTicks > 0;
        }
    }
}
