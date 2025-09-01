package tech.dlii.opencomputers.server.machine;

import tech.dlii.opencomputers.api.machine.Architecture;
import tech.dlii.opencomputers.api.machine.MachineAPI;

import java.util.Collection;
import java.util.LinkedHashSet;

public class MachineRegistry implements MachineAPI {

    private LinkedHashSet<Class<? extends Architecture>> architectures = new LinkedHashSet<>();

    @Override
    public void add(Class<? extends Architecture> architecture) {

    }

    @Override
    public Collection<Class<? extends Architecture>> architectures() {
        return this.architectures.stream().toList();
    }

    @Override
    public String getArchitectureName(Class<? extends Architecture> architecture) {
        return "";
    }
}
