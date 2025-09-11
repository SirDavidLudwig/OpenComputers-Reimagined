package tech.dlii.opencomputers.api.machine;

// Use string names
public enum MachineState {
    /** The computer is not running and there is no architecture state. */
    Stopped("stopped"),

    /** Booting up, doing the first run to initialize the kernel and libs. */
    Starting("starting"),

    /** The computer is currently running and executing code. */
    Running("running"),

    /** The computer is currently shutting down. */
    Stopping("stopping"),

    /** The computer is currently rebooting. */
    Restarting("restarting"),

    /** The computer is paused and waiting for the game to resume. */
    Paused("paused"),

    /** The computer executor is waiting for a synchronized call to be made. */
    SynchronizedCall("synchronized_call"),

    /** The computer should resume with the result of a synchronized call. */
    SynchronizedReturn("synchronized_return"),

    /** The computer will resume as soon as possible */
    Yielded("yielded"),

    /** The computer is yielding for a longer amount of time. */
    Sleeping("sleeping");

    private final String value;

    MachineState(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
