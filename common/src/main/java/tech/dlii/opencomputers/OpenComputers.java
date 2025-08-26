package tech.dlii.opencomputers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.dlii.opencomputers.block.Blocks;
import tech.dlii.opencomputers.block.entity.BlockEntityTypes;
import tech.dlii.opencomputers.item.Items;

public final class OpenComputers {

    public static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        CreativeTabs.initialize();
        Blocks.initialize();
        Items.initialize();
        // BlockEntityTypes.initialize(); // Invoked in platform-specific code
    }
}
