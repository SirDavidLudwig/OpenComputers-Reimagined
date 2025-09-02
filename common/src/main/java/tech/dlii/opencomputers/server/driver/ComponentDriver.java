package tech.dlii.opencomputers.server.driver;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.DriverItem;
import tech.dlii.opencomputers.api.driver.item.SlotType;

public class ComponentDriver implements DriverItem {

    @Override
    public boolean worksWith(ItemStack stack) {
        return false;
    }

    @Override
    public String slotType(ItemStack stack) {
        return SlotType.NONE;
    }

    @Override
    public int tier(ItemStack stack) {
        return Tier.ONE;
    }
}
