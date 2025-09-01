package tech.dlii.opencomputers.server.machine;

import tech.dlii.opencomputers.api.machine.Architecture;
import tech.dlii.opencomputers.api.machine.MachineAPI;

import java.util.Collection;
import java.util.LinkedHashSet;

public class MachineRegistry implements MachineAPI {

    private LinkedHashSet<Class<? extends Architecture>> architectures = new LinkedHashSet<>();

    @Override
    public void add(Class<? extends Architecture> architecture) {
        architectures.add(architecture);
    }

    @Override
    public Collection<Class<? extends Architecture>> architectures() {
        return this.architectures.stream().toList();
    }

    @Override
    public String getArchitectureName(Class<? extends Architecture> architecture) {
        Architecture.Name annotation = architecture.getAnnotation(Architecture.Name.class);
        if (annotation == null) {
            return architecture.getSimpleName();
        }
        return annotation.value();
    }

    public Class<? extends Architecture> getDefaultArchitecture() {
        return architectures.getFirst();
    }
}
