package tech.dlii.opencomputers.server.driver;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.CallBudget;
import tech.dlii.opencomputers.api.driver.item.MutableControlProcessingUnit;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.common.component.DataComponents;
import tech.dlii.opencomputers.common.item.CPUItem;
import tech.dlii.opencomputers.common.item.Items;
import tech.dlii.opencomputers.config.Configuration;

import java.util.List;

public class CPUDriver extends ComponentDriver implements MutableControlProcessingUnit, CallBudget {

    private final List<RegistrySupplier<Item>> COMPATIBLE_ITEMS = List.of(
            Items.CPU_TIER_1,
            Items.CPU_TIER_2,
            Items.CPU_TIER_3
    );

    @Override
    public List<ResourceLocation> architectures() {
        return API.architectures.architectures().keySet().stream().toList();
    }

    @Override
    public ResourceLocation architecture(ItemStack stack) {
        ResourceLocation architecture = stack.get(DataComponents.ARCHITECTURE.get());
        if (architecture == null || !API.architectures.has(architecture)) {
            // Assign default architecture
            architecture = API.architectures.defaultArchitecture().getKey();
            setArchitecture(stack, architecture);
        }
        return architecture;
    }

    @Override
    public void setArchitecture(ItemStack stack, ResourceLocation identifier) {
        if (!worksWith(stack)) {
            throw new IllegalArgumentException("Unsupported CPU type.");
        }
        if (!API.architectures.has(identifier)) {
            return;
        }
        stack.set(DataComponents.ARCHITECTURE.get(), identifier);
    }

    @Override
    public int supportedComponents(ItemStack stack) {
        return Configuration.CPU_COMPONENT_COUNT[tier(stack)];
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        Item stackItem = stack.getItem();
        return COMPATIBLE_ITEMS.stream().anyMatch(item -> item.get() == stackItem);
    }

    @Override
    public String slotType(ItemStack stack) {
        return SlotType.CPU;
    }

    @Override
    public int tier(ItemStack stack) {
        if (stack.getItem() instanceof CPUItem cpu) {
            return cpu.tier();
        }
        return Tier.ONE;
    }

    @Override
    public double getCallBudget(ItemStack stack) {
        return Configuration.CALL_BUDGETS[Math.clamp(tier(stack), Tier.ONE, Tier.THREE)];
    }
}
