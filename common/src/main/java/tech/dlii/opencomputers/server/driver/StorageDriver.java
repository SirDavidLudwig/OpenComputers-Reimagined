package tech.dlii.opencomputers.server.driver;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import tech.dlii.opencomputers.common.item.Items;

import java.util.List;

public class StorageDriver extends ComponentDriver {

    @Override
    protected List<RegistrySupplier<Item>> compatibleItems() {
        return List.of(
                Items.HDD_TIER_1,
                Items.HDD_TIER_2,
                Items.HDD_TIER_3,
                Items.FLOPPY
        );
    }
}
