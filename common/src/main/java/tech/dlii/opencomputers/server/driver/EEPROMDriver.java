package tech.dlii.opencomputers.server.driver;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import tech.dlii.opencomputers.common.item.Items;

import java.util.List;

public class EEPROMDriver extends ComponentDriver {
    @Override
    protected List<RegistrySupplier<Item>> compatibleItems() {
        return List.of(Items.EEPROM);
    }
}
