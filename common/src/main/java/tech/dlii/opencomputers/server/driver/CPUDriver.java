package tech.dlii.opencomputers.server.driver;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.CallBudget;
import tech.dlii.opencomputers.api.driver.item.MutableControlProcessingUnit;
import tech.dlii.opencomputers.common.component.DataComponents;
import tech.dlii.opencomputers.common.config.Configuration;
import tech.dlii.opencomputers.common.item.Items;

import java.util.List;

public class CPUDriver extends ComponentDriver implements MutableControlProcessingUnit, CallBudget {

    @Override
    protected List<RegistrySupplier<Item>> compatibleItems() {
        return List.of(
                Items.CPU_TIER_1,
                Items.CPU_TIER_2,
                Items.CPU_TIER_3
        );
    }

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
    public double getCallBudget(ItemStack stack) {
        return Configuration.CALL_BUDGETS[Math.clamp(tier(stack), Tier.ONE, Tier.THREE)];
    }
}
