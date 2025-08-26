package tech.dlii.opencomputers.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.neoforge.block.entity.BlockEntityTypesNeoForge;

@Mod(API.MOD_ID)
public final class OpenComputersNeoForge {
    public OpenComputersNeoForge(IEventBus modBus) {
        // Run our common setup.
        OpenComputers.init();

        // Register the block entity types.
        BlockEntityTypesNeoForge.initialize();
    }
}
