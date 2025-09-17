package tech.dlii.opencomputers.server.machine;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.machine.*;
import tech.dlii.opencomputers.api.machine.architecture.Architecture;
import tech.dlii.opencomputers.api.machine.architecture.ExecutionResult;
import tech.dlii.opencomputers.api.network.Visibility;
import tech.dlii.opencomputers.api.network.prefab.AbstractManagedEnvironment;
import tech.dlii.opencomputers.common.config.Configuration;
import tech.dlii.opencomputers.server.machine.architecture.luaj.LuaJLuaArchitecture;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Machine extends AbstractManagedEnvironment implements Runnable, tech.dlii.opencomputers.api.machine.Machine {

    private Architecture architecture = null;
    private MachineHost host;

    private Queue<Signal> signals = new ArrayDeque<>();
    private Stack<MachineState> state = new Stack<>();
    private long worldTime = 0L;
    private long upTime = 0L;
    private long cpuStartTime = 0L;
    private long cpuTotalTime = 0L;
    private long remainingIdleTicks = 0L;
    private long remainingPauseTicks = 0L;
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
//        worldTime = host.level().getDayTime()
        upTime++;
        if (remainingIdleTicks > 0) {
            remainingIdleTicks--;
        }

        synchronized (state) {
            switch (state.peek()) {
                case Starting:
                    switchTo(MachineState.Yielded);
                    break;
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

                // Check if someone called pause() or stop() in the meantime.
//                synchronized (state) {
//
//                }
            } catch (Throwable throwable) {
                OpenComputers.LOGGER.warn("Architecture's runThreaded threw an error. This should never happen!", throwable);
                crash("gui.Error.InternalError");
            }
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
    public @Nullable Signal popSignal() {
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
        OpenComputers.LOGGER.info("Game is paused: " + paused);
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
        return false;
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
