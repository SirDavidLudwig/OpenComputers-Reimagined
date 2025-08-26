package tech.dlii.opencomputers.fabric.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.fabricmc.api.ClientModInitializer;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.client.renderer.color.CaseBlockColor;

public final class OpenComputersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ColorHandlerRegistry.registerBlockColors(new CaseBlockColor(), Blocks.CASE1.get(), Blocks.CASE2.get(), Blocks.CASE3.get(), Blocks.CASE_CREATIVE.get());
    }
}
