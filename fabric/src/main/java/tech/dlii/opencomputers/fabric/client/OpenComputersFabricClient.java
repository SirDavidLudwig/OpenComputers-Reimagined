package tech.dlii.opencomputers.fabric.client;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.ClientModInitializer;
import tech.dlii.opencomputers.client.gui.CustomDataComponentTooltips;
import tech.dlii.opencomputers.client.gui.screen.CaseScreen;
import tech.dlii.opencomputers.client.renderer.blockentity.ScreenBlockEntityRenderer;
import tech.dlii.opencomputers.client.renderer.color.ScreenBlockColor;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.client.renderer.color.CaseBlockColor;
import tech.dlii.opencomputers.common.block.entity.BlockEntityTypes;
import tech.dlii.opencomputers.common.inventory.MenuTypes;

public final class OpenComputersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        CustomDataComponentTooltips.initialize();

        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ColorHandlerRegistry.registerBlockColors(new CaseBlockColor(), Blocks.CASE_TIER_1.get(), Blocks.CASE_TIER_2.get(), Blocks.CASE_TIER_3.get(), Blocks.CASE_CREATIVE.get());
        ColorHandlerRegistry.registerBlockColors(new ScreenBlockColor(), Blocks.SCREEN_TIER_1.get(), Blocks.SCREEN_TIER_2.get(), Blocks.SCREEN_TIER_3.get());

        BlockEntityRendererRegistry.register(BlockEntityTypes.SCREEN.get(), ScreenBlockEntityRenderer::new);

        ClientLifecycleEvent.CLIENT_STARTED.register(client -> {
            MenuRegistry.registerScreenFactory(MenuTypes.CASE.get(), CaseScreen::new);
        });
    }
}
