package tech.dlii.opencomputers.client.gui.neoforge;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.GuiElementRenderState;

public final class ExtendedGuiGraphicsImpl {
    public static void submitGuiElement(GuiGraphics guiGraphics, GuiElementRenderState renderState) {
        guiGraphics.submitGuiElementRenderState(renderState);
    }

    public static ScreenRectangle peekScissorArea(GuiGraphics guiGraphics) {
        return guiGraphics.peekScissorStack();
    }
}

