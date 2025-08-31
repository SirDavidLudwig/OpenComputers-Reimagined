package tech.dlii.opencomputers.integration.opencomputers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.common.item.CPU;
import tech.dlii.opencomputers.common.item.Memory;
import tech.dlii.opencomputers.common.item.Items;

import java.util.List;

public class DriverMemory extends OpenComputersItem {

    private final List<Item> COMPATIBLE_ITEMS;

    public DriverMemory() {
        this.COMPATIBLE_ITEMS = List.of(
                Items.MEMORY1.get(),
                Items.MEMORY2.get(),
                Items.MEMORY3.get(),
                Items.MEMORY4.get(),
                Items.MEMORY5.get(),
                Items.MEMORY6.get()
        );
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        return this.COMPATIBLE_ITEMS.contains(stack.getItem());
    }

    @Override
    public String slot(ItemStack stack) {
        return SlotType.MEMORY;
    }

    @Override
    public int tier(ItemStack stack) {
        if (stack.getItem() instanceof Memory memory) {
            return memory.tier();
        }
        return Tier.ONE;
    }

    @Override
    public CompoundTag dataTag(ItemStack stack) {
        return super.dataTag(stack);
    }
}
