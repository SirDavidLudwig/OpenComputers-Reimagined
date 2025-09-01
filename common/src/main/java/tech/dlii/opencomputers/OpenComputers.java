package tech.dlii.opencomputers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.component.DataComponents;
import tech.dlii.opencomputers.common.item.Items;
import tech.dlii.opencomputers.common.inventory.MenuTypes;
import tech.dlii.opencomputers.integration.Mods;
import tech.dlii.opencomputers.server.driver.DriverRegistry;
import tech.dlii.opencomputers.server.machine.MachineRegistry;
import tech.dlii.opencomputers.server.machine.architecture.luac.NativeLua52Architecture;
import tech.dlii.opencomputers.server.machine.architecture.luac.NativeLua53Architecture;
import tech.dlii.opencomputers.server.machine.architecture.luac.NativeLua54Architecture;

public final class OpenComputers {

    public static final Logger LOGGER = LogManager.getLogger();

    public static void initialize() {
        CreativeTabs.initialize();
        API.driver = new DriverRegistry();
        API.machine = new MachineRegistry();

        API.machine.add(NativeLua52Architecture.class);
        API.machine.add(NativeLua53Architecture.class);
        API.machine.add(NativeLua54Architecture.class);

        DataComponents.initialize();

        Blocks.initialize();
        Items.initialize();
        // BlockEntityTypes.initialize(); // Invoked in platform-specific code
        MenuTypes.initialize();
    }

    public static void setup() {
        // Finish with inter-mod initialization
        Mods.initialize();
    }
}
