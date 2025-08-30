package tech.dlii.opencomputers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.item.Items;
import tech.dlii.opencomputers.common.container.MenuTypes;

public final class OpenComputers {

    public static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        CreativeTabs.initialize();
        Blocks.initialize();
        Items.initialize();
        // BlockEntityTypes.initialize(); // Invoked in platform-specific code
        MenuTypes.initialize();
    }
}
