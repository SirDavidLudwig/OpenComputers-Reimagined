package tech.dlii.opencomputers.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ImageButton extends Button {

    public final ResourceLocation image;
    public final boolean canToggle;

    public boolean toggled = false;
    public boolean hoverOverride = false;

    public ImageButton(int x,
                          int y,
                          int width,
                          int height,
                          boolean canToggle,
                          ResourceLocation image,
                          Component component,
                          OnPress onPress) {
        super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
        this.image = image;
        this.canToggle = canToggle;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        if (!visible) {
            return;
        }
        boolean drawHover = hoverOverride || isHovered();

        float u0 = toggled ? width : 0.0f;
        float v0 = drawHover ? height : 0.0f;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, image, getX(), getY(), u0, v0, width, height, width*2, height*2);
    }
}
