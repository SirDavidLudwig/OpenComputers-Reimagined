package tech.dlii.opencomputers.server.driver;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.DriverItem;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.common.item.ComponentItem;

import java.util.List;

public abstract class AbstractComponentDriver implements DriverItem {

    private final List<RegistrySupplier<Item>> COMPATIBLE_ITEMS;

    public AbstractComponentDriver() {
        this.COMPATIBLE_ITEMS = compatibleItems();
    }

    protected List<RegistrySupplier<Item>> compatibleItems() {
        return List.of();
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        Item stackItem = stack.getItem();
        return COMPATIBLE_ITEMS.stream().anyMatch(item -> item.get() == stackItem);
    }

    @Override
    public String slotType(ItemStack stack) {
        if (stack.getItem() instanceof ComponentItem component) {
            return component.slotType();
        }
        return SlotType.NONE;
    }

    @Override
    public int tier(ItemStack stack) {
        if (stack.getItem() instanceof ComponentItem component) {
            return component.tier();
        }
        return Tier.NONE;
    }
}
