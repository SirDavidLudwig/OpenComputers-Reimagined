package tech.dlii.opencomputers.integration.opencomputers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.DriverItem;

public class ComponentDriver implements DriverItem {

    @Override
    public boolean worksWith(ItemStack stack) {
        return false;
    }

    @Override
    public String slotType(ItemStack stack) {
        return "";
    }

    @Override
    public int tier(ItemStack stack) {
        return Tier.ONE;
    }

    @Override
    public CompoundTag dataTag(ItemStack stack) {
        return null;
    }
}
