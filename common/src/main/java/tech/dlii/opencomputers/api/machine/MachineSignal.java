package tech.dlii.opencomputers.api.machine;

/**
 * A single signal that was queued on a machine.
 * <br>
 * This interface is not intended to be implemented, it only serves as a return
 * type for {@link Machine#popSignal()}.
 */
public record MachineSignal(String name, Object[] args) {
}
