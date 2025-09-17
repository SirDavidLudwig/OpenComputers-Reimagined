package tech.dlii.opencomputers.server.driver;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.network.EnvironmentHost;
import tech.dlii.opencomputers.api.network.ManagedEnvironment;
import tech.dlii.opencomputers.common.item.HardDriveItem;
import tech.dlii.opencomputers.common.item.Items;
import tech.dlii.opencomputers.server.component.FileSystemComponent;

import java.util.List;

public class FileSystemDriver extends AbstractComponentDriver {

    @Override
    protected List<RegistrySupplier<Item>> compatibleItems() {
        return List.of(
                Items.HDD_TIER_1,
                Items.HDD_TIER_2,
                Items.HDD_TIER_3,
                Items.FLOPPY
        );
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        if (stack.getItem() instanceof HardDriveItem hdd) {
            return createEnvironment(stack, hdd.size()*1024, hdd.platterCount(), host, hdd.tier() + 2);
        }
        return null;
    }

    protected ManagedEnvironment createEnvironment(ItemStack stack, int capacityBytes, int platterCount, EnvironmentHost host, int speed) {
        return null;
    }
}
