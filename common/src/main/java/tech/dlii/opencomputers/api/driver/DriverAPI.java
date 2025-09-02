package tech.dlii.opencomputers.api.driver;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.api.driver.item.DriverItem;

public interface DriverAPI {
    /**
     * Registers a new driver for an item component.
     * <br>
     * Item components can inserted into a computers component slots. They have
     * to specify their type, to determine into which slots they can fit.
     * <br>
     * This must be called in the init phase, <em>not</em> the pre- or post-init
     * phases.
     *
     * @param driver the driver for an item component.
     */
    void register(DriverItem driver);

    /**
     * Looks up a driver for the specified item stack.
     * <br>
     * Note that unlike for blocks, there can always only be one item driver
     * per item. If there are multiple ones, the first one that was registered
     * will be used.
     * <br>
     * This is a context-agnostic variant used mostly for "house-keeping"
     * stuff, such as querying slot types and tier.
     *
     * @param stack the item stack to get a driver for.
     * @return a driver for the item, or {@code null} if there is none.
     */
    @Nullable
    DriverItem driverFor(ItemStack stack);
}
