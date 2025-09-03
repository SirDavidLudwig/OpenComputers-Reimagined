package tech.dlii.opencomputers.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.client.gui.screen.CaseScreen;
import tech.dlii.opencomputers.client.renderer.blockentity.ScreenBlockEntityRenderer;
import tech.dlii.opencomputers.client.renderer.color.CaseBlockColor;
import tech.dlii.opencomputers.client.renderer.color.ScreenBlockColor;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.block.entity.BlockEntityTypes;
import tech.dlii.opencomputers.common.block.entity.ScreenBlockEntity;
import tech.dlii.opencomputers.common.inventory.MenuTypes;

@Mod(value=API.MOD_ID, dist=Dist.CLIENT)
public final class OpenComputersNeoForgeClient {
    public OpenComputersNeoForgeClient(IEventBus modBus) {
        modBus.register(OpenComputersNeoForgeClient.class);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(new CaseBlockColor(), Blocks.CASE_TIER_1.get(), Blocks.CASE_TIER_2.get(), Blocks.CASE_TIER_3.get(), Blocks.CASE_CREATIVE.get());
        event.register(new ScreenBlockColor(), Blocks.SCREEN_TIER_1.get(), Blocks.SCREEN_TIER_2.get(), Blocks.SCREEN_TIER_3.get());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityTypes.SCREEN.get(), ScreenBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuTypes.CASE.get(), CaseScreen::new);
    }
}
