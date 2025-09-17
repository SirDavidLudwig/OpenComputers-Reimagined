package tech.dlii.opencomputers.server.driver;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.network.EnvironmentHost;
import tech.dlii.opencomputers.api.network.ManagedEnvironment;
import tech.dlii.opencomputers.common.item.Items;
import tech.dlii.opencomputers.server.component.GPUComponent;

import java.util.List;

public class GPUDriver extends AbstractComponentDriver {
    @Override
    protected List<RegistrySupplier<Item>> compatibleItems() {
        return List.of(
                Items.GPU_TIER_1,
                Items.GPU_TIER_2,
                Items.GPU_TIER_3
        );
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new GPUComponent(tier(stack));
    }
}
