package tech.dlii.opencomputers.client.gui.fabric;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.GuiElementRenderState;

public class ExtendedGuiGraphicsImpl {
    public static void submitGuiElement(GuiGraphics guiGraphics, GuiElementRenderState renderState) {
        guiGraphics.guiRenderState.submitGuiElement(renderState);
    }

    public static ScreenRectangle peekScissorArea(GuiGraphics guiGraphics) {
        return guiGraphics.scissorStack.peek();
    }
}
