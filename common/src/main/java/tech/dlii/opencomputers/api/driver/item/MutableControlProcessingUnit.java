package tech.dlii.opencomputers.api.driver.item;

import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.machine.Architecture;

/**
 * May be implemented in processor drivers of processors that can be reconfigured.
 * <br>
 * This is the case for OC's built-in CPUs, for example, which can be reconfigured
 * to any registered architecture. It a CPU has such a driver, it may also be
 * reconfigured by the machine it is running in (e.g. in the Lua case via
 * {@code computer.setArchitecture}).
 */
public interface MutableControlProcessingUnit extends ControlProcessingUnit {
    /**
     * Get a list of all architectures supported by this processor.
     */
    java.util.Collection<Class<? extends Architecture>> allArchitectures();

    /**
     * Set the architecture to use for the specified processor.
     *
     * @param stack        the processor to set the architecture for.
     * @param architecture the architecture to use on the processor.
     */
    void setArchitecture(ItemStack stack, Class<? extends Architecture> architecture);
}
