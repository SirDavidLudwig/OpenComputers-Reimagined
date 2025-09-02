package tech.dlii.opencomputers.integration.opencomputers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.CallBudget;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.common.item.MemoryItem;
import tech.dlii.opencomputers.common.item.Items;
import tech.dlii.opencomputers.config.Configuration;

import java.util.List;

public class DriverMemory extends ComponentDriver implements tech.dlii.opencomputers.api.driver.item.Memory, CallBudget {

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
    public double amount(ItemStack stack) {
        if (!(stack.getItem() instanceof MemoryItem memory)) {
            return 0.0;
        }
        return Configuration.RAM_SIZES[memory.tier()];
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        return this.COMPATIBLE_ITEMS.contains(stack.getItem());
    }

    @Override
    public String slotType(ItemStack stack) {
        return SlotType.MEMORY;
    }

    @Override
    public int tier(ItemStack stack) {
        if (!(stack.getItem() instanceof MemoryItem memory)) {
            return Tier.ONE;
        }
        return memory.tier() / 2;
    }

    @Override
    public double getCallBudget(ItemStack stack) {
        return Configuration.CALL_BUDGETS[Math.clamp(tier(stack), Tier.ONE, Tier.THREE)];
    }
}
