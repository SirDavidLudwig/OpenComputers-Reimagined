package tech.dlii.opencomputers.server.machine;

import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.machine.Architecture;
import tech.dlii.opencomputers.api.machine.MachineHost;
import tech.dlii.opencomputers.api.machine.MachineState;
import tech.dlii.opencomputers.api.machine.Signal;
import tech.dlii.opencomputers.server.machine.architecture.luaj.LuaJLuaArchitecture;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Machine implements tech.dlii.opencomputers.api.machine.Machine {

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
        OpenComputers.LOGGER.info("Created machine");
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

    @Override
    public boolean start() {
        init();
        return false;
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

    public boolean init() {
        if (architecture == null) {
            architecture = new LuaJLuaArchitecture(this);
            return false;
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
}
