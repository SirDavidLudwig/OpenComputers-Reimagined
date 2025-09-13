package tech.dlii.opencomputers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.component.CustomDataComponents;
import tech.dlii.opencomputers.common.item.Items;
import tech.dlii.opencomputers.common.inventory.MenuTypes;
import tech.dlii.opencomputers.common.network.PacketTypes;
import tech.dlii.opencomputers.config.Config;
import tech.dlii.opencomputers.integration.Mods;
import tech.dlii.opencomputers.server.OpenComputersGameRules;
import tech.dlii.opencomputers.server.driver.Drivers;
import tech.dlii.opencomputers.server.machine.Machines;
import tech.dlii.opencomputers.server.machine.architecture.Architectures;
import tech.dlii.opencomputers.server.network.Networks;

public final class OpenComputers {

    public static final Logger LOGGER = LogManager.getLogger();

    public static void initialize() {
        Architectures.initialize();
        Drivers.initialize();
        Machines.initialize();
        Networks.initialize();

        OpenComputersGameRules.initialize();
        CreativeTabs.initialize();
        CustomDataComponents.initialize();

        Blocks.initialize();
        Items.initialize();
        // BlockEntityTypes.initialize(); // Invoked in platform-specific code

        Config.read();

        MenuTypes.initialize();
    }

    public static void setup() {
        // Finish with inter-mod initialization
        Mods.initialize();
        PacketTypes.initialize();
    }
}
