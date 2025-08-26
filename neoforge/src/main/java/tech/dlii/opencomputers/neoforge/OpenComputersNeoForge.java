package tech.dlii.opencomputers.neoforge;

import net.neoforged.fml.common.Mod;

import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.neoforge.block.entity.BlockEntityTypesNeoForge;

@Mod(API.MOD_ID)
public final class OpenComputersNeoForge {
    public OpenComputersNeoForge() {
        // Run our common setup.
        OpenComputers.init();

        // Register the block entity types.
        BlockEntityTypesNeoForge.initialize();
    }
}
