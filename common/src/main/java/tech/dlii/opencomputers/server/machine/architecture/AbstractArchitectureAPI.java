package tech.dlii.opencomputers.server.machine.architecture;

import tech.dlii.opencomputers.api.machine.Machine;

import java.util.Map;

public abstract class AbstractArchitectureAPI {

    private Machine machine;

    public AbstractArchitectureAPI(Machine machine) {
        this.machine = machine;
    }

    public abstract void initialize();

    protected Map<String, String> components() {
        return machine.components();
    }

    public Machine machine() {
        return machine;
    }
}
