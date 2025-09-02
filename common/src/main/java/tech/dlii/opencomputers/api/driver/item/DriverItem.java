package tech.dlii.opencomputers.api.driver.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

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
