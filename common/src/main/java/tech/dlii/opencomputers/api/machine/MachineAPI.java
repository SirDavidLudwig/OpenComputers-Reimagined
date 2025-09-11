package tech.dlii.opencomputers.api.machine;

import java.util.Collection;

public interface MachineAPI {
    /**
     * Creates a new machine for the specified host.
     * <br>
     * You are responsible for calling update and save / load functions on the
     * machine for it to work correctly.
     *
     * @param host the owner object of the machine, providing context.
     * @return the newly created machine.
     * @throws IllegalArgumentException if the specified architecture is invalid.
     */
    Machine create(MachineHost host);
}
