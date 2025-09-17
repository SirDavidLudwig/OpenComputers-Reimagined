package tech.dlii.opencomputers.api.driver.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.network.EnvironmentHost;
import tech.dlii.opencomputers.api.network.ManagedEnvironment;

public interface DriverItem {
    /**
     * Used to determine the item types this driver handles.
     * <br>
     * This is used to determine which driver to use for an item when it should
     * be installed in a computer. Note that the return value should not change
     * over time; if it does, though, an already installed component will not
     * be ejected, since this value is only checked when adding components.
     * <br>
     * This is a context-agnostic variant used mostly for "house-keeping"
     * stuff, such as querying slot types and tier.
     *
     * @param stack the item to check.
     * @return {@code true} if the item is supported; {@code false} otherwise.
     */
    boolean worksWith(ItemStack stack);

    /**
     * Create a new managed environment interfacing the specified item.
     * <br>
     * This is used to connect the component to the component network when it is
     * added to a computer, for example. The only kind of component that does
     * not need to be connected to the component network is probably memory, and
     * there's a built-in driver for that. You may still opt to not implement
     * this - i.e. it is safe to return {@code null} here.
     * <br>
     * Keep in mind that the host's location may change if the owner is
     * a robot. This is important if you cache the location somewhere. For
     * example, the wireless network card checks in a robot movement event
     * handler for position changes to update the index structure used for
     * receiver look-up.
     * <br>
     * This is expected to return a <em>new instance</em> each time it is
     * called. The created instance's life cycle is managed by the host
     * that caused its creation.
     *
     * @param stack the item stack for which to get the environment.
     * @param host  the host the environment will be managed by.
     * @return the environment for that item.
     */
    ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host);

    /**
     * The slot type of the specified item this driver supports.
     * <br>
     * This is used to determine into which slot of a computer the components
     * this driver supports may go. This will only be called if a previous call
     * to {@link #worksWith} with the same stack returned true.
     *
     * @param stack the item stack to get the slot type for.
     * @return the slot type of the specified item.
     * @see li.cil.oc.api.driver.item.Slot
     */
    String slotType(ItemStack stack);

    /**
     * The tier of the specified item this driver supports.
     * <br>
     * This is used to determine into which slot of a computer the components
     * this driver supports may go. This will only be called if a previous call
     * to {@link #worksWith} with the same stack returned true.
     * <br>
     * <em>Important</em>: tiers are zero-indexed.
     *
     * @param stack the item stack to get the tier for.
     * @return the tier of the specified item.
     */
    int tier(ItemStack stack);
}
