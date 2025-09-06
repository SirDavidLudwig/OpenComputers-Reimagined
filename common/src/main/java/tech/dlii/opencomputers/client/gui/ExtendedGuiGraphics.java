package tech.dlii.opencomputers.client.gui;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.GuiElementRenderState;

/**
 * This class provides platform-specific calls for render state calls
 */
public class ExtendedGuiGraphics {
    @ExpectPlatform
    public static void submitGuiElement(GuiGraphics guiGraphics, GuiElementRenderState renderState) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ScreenRectangle peekScissorArea(GuiGraphics guiGraphics) {
        throw new AssertionError();
    }
}
