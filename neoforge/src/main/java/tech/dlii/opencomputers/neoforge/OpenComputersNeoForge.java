package tech.dlii.opencomputers.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.neoforge.block.entity.BlockEntityTypesNeoForge;
import tech.dlii.opencomputers.neoforge.integration.NeoForgeModMenu;

@Mod(API.MOD_ID)
public final class OpenComputersNeoForge {
    public OpenComputersNeoForge(IEventBus modBus) {
        modBus.register(OpenComputersNeoForge.class);

        // Run our common setup.
        OpenComputers.initialize();

        // Register the block entity types.
        BlockEntityTypesNeoForge.initialize();

        // Register ClothConfig screen.
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, NeoForgeModMenu::new);
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        OpenComputers.setup();
    }
}
