package tech.dlii.opencomputers.api.machine;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface ArchitectureAPI {
    /**
     * Register an architecture that can be used to create new machines.
     * <br>
     * Note that although registration is optional, it is strongly recommended
     * to allow {@link #architectures()} to be useful.
     *
     * @param name
     * @param architecture the architecture to register.
     * @throws IllegalArgumentException if the specified architecture is invalid.
     */
    void register(ResourceLocation identifier, Class<? extends Architecture> architecture);

    /**
     * A list of all <em>registered</em> architectures.
     * <br>
     * Note that registration is optional, although automatic when calling
     * {@link #create(li.cil.oc.api.machine.MachineHost)} with a not yet
     * registered architecture. What this means is that unless a mod providing
     * a custom architecture also registers it, you may not see it in this list
     * until it also created a new machine using that architecture.
     */
    Map<ResourceLocation, Class<? extends Architecture>> architectures();

    /**
     * The default architecture entry.
     */
    Map.Entry<ResourceLocation, Class<? extends Architecture>> defaultArchitecture();

    /**
     * Check if the given architecture identifier is registered
     * @param identifier the architecture identifier to check
     */
    boolean has(ResourceLocation identifier);
}
