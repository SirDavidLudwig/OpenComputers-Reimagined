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

    /**
     * Get the tag compound based on the item stack to use for persisting the
     * environment associated with the specified item stack.
     * <br>
     * This is only used if the item has an environment. This must always be a
     * child tag of the item stack's own tag compound, it will not be saved
     * otherwise. Use this in the unlikely case that the default name collides
     * with something. The built-in components use a child tag-compound with
     * the name {@code oc:data}, which will also be used if this returns
     * {@code null}.
     * <br>
     * This tag will be passed to the environment's
     * {@link li.cil.oc.api.Persistable#saveData saveData} and
     * {@link li.cil.oc.api.Persistable#loadData loadData} methods when
     * appropriate (world save / load and when removed from their hosting
     * inventory).
     *
     * @param stack the item to get the child tag from.
     * @return the tag to use for saving and loading, or {@code null} to use
     * the default tag {@code oc:data}.
     */
    CompoundTag dataTag(ItemStack stack);
}
