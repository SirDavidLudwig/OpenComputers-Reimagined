package tech.dlii.opencomputers.api.driver;

import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.driver.item.DriverItem;

public final class Driver {
    /**
     * Registers a new item driver.
     * <br>
     * Item components can be inserted into a computer's component slots. They have
     * to specify their type to determine into which slots they can fit.
     * <br>
     * This must be called in the init phase, <em>not</em> the pre- or post-init
     * phases.
     *
     * @param driver the driver to register.
     */
    public static void add(final DriverItem driver) {
        if (API.driver != null) {
            API.driver.add(driver);
        }
    }

    /**
     * Looks up a driver for the specified item stack.
     * <br>
     * Note that unlike for blocks, there can always only be one item driver
     * per item. If there are multiple ones, the first one that was registered
     * will be used.
     *
     * @param stack the item stack to get a driver for.
     * @param host  the type that will host the environment created by returned driver.
     * @return a driver for the item, or {@code null} if there is none.
     */
//    public static DriverItem driverFor(ItemStack stack, Class<? extends EnvironmentHost> host) {
//        if (API.driver != null)
//            return API.driver.driverFor(stack, host);
//        return null;
//    }

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
    public static DriverItem driverFor(ItemStack stack) {
//        if (API.driver != null)
//            return API.driver.driverFor(stack);
        return null;
    }
}
