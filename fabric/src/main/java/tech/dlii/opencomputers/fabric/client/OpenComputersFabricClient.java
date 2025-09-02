package tech.dlii.opencomputers.fabric.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.fabricmc.api.ClientModInitializer;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.client.renderer.color.ScreenBlockColor;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.client.renderer.color.CaseBlockColor;

public final class OpenComputersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ColorHandlerRegistry.registerBlockColors(new CaseBlockColor(), Blocks.CASE_TIER_1.get(), Blocks.CASE_TIER_2.get(), Blocks.CASE_TIER_3.get(), Blocks.CASE_CREATIVE.get());
        ColorHandlerRegistry.registerBlockColors(new ScreenBlockColor(), Blocks.SCREEN_TIER_1.get(), Blocks.SCREEN_TIER_2.get(), Blocks.SCREEN_TIER_3.get());
    }
}
