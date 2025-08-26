package tech.dlii.opencomputers.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.client.renderer.color.CaseBlockColor;
import tech.dlii.opencomputers.common.block.Blocks;

@Mod(value=API.MOD_ID, dist=Dist.CLIENT)
public final class OpenComputersNeoForgeClient {
    public OpenComputersNeoForgeClient(IEventBus modBus) {
        OpenComputers.LOGGER.info("Client Initialized");
        modBus.addListener(OpenComputersNeoForgeClient::registerBlockColors);;
    }

    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        OpenComputers.LOGGER.info("Registering     Block Colors");
        event.register(new CaseBlockColor(), Blocks.CASE1.get(), Blocks.CASE2.get(), Blocks.CASE3.get(), Blocks.CASE_CREATIVE.get());
    }
}
