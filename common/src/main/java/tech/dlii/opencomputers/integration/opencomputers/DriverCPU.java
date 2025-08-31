package tech.dlii.opencomputers.integration.opencomputers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.common.item.CPU;
import tech.dlii.opencomputers.common.item.Items;

import java.util.List;

public class DriverCPU extends OpenComputersItem {

    private final List<Item> COMPATIBLE_ITEMS;

    public DriverCPU() {
        this.COMPATIBLE_ITEMS = List.of(
                Items.CPU_TIER_1.get(),
                Items.CPU_TIER_2.get(),
                Items.CPU_TIER_3.get()
        );
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        return COMPATIBLE_ITEMS.contains(stack.getItem());
    }

    @Override
    public String slot(ItemStack stack) {
        return SlotType.CPU;
    }

    @Override
    public int tier(ItemStack stack) {
        if (stack.getItem() instanceof CPU cpu) {
            return cpu.tier();
        }
        return Tier.ONE;
    }

    @Override
    public CompoundTag dataTag(ItemStack stack) {
        return super.dataTag(stack);
    }
}
