package tech.dlii.opencomputers.fabric;

import net.fabricmc.api.ModInitializer;

import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.fabric.block.entity.BlockEntityTypesFabric;

public final class OpenComputersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        OpenComputers.init();

        // Register the block entity types.
        BlockEntityTypesFabric.initialize();
    }
}
