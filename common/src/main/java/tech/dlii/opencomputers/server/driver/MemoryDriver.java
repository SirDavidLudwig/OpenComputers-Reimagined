package tech.dlii.opencomputers.server.driver;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.CallBudget;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.common.config.Configuration;
import tech.dlii.opencomputers.common.item.MemoryItem;
import tech.dlii.opencomputers.common.item.Items;

import java.util.List;

public class MemoryDriver extends ComponentDriver implements tech.dlii.opencomputers.api.driver.item.Memory, CallBudget {

    private final List<RegistrySupplier<Item>> COMPATIBLE_ITEMS = List.of(
            Items.MEMORY1,
            Items.MEMORY2,
            Items.MEMORY3,
            Items.MEMORY4,
            Items.MEMORY5,
            Items.MEMORY6
    );

    @Override
    public double amount(ItemStack stack) {
        if (!(stack.getItem() instanceof MemoryItem memory)) {
            return 0.0;
        }
        return Configuration.RAM_SIZES[memory.tier()];
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        Item stackItem = stack.getItem();
        return COMPATIBLE_ITEMS.stream().anyMatch(item -> item.get() == stackItem);
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
