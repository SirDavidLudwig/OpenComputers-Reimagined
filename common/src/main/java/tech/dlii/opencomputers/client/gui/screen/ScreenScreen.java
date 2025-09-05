package tech.dlii.opencomputers.client.gui.screen;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
import tech.dlii.opencomputers.client.Textures;
import tech.dlii.opencomputers.client.font.Fonts;
import tech.dlii.opencomputers.client.font.Glyph;
import tech.dlii.opencomputers.client.gui.widget.TerminalWidget;
import tech.dlii.opencomputers.common.machine.TextBuffer;

import java.util.function.Supplier;

public class ScreenScreen extends Screen {

    private TextBuffer buffer;
    private int tier;
    private Supplier<Boolean> hasKeyboard;

    public static final int BORDER_THICKNESS = 8;
    public static final int INNER_MARGIN = 1;
    public static final int OUTER_MARGIN = 7;

    private TerminalWidget terminalWidget;

    public ScreenScreen(TextBuffer buffer, int tier, Supplier<Boolean> hasKeyboard) {
        super(Component.translatable("screen.opencomputers.screen"));
        this.buffer = buffer;
        this.tier = tier;
        this.hasKeyboard = hasKeyboard;
    }

    @Override
    protected void init() {
        super.init();

        // Actual buffer size
        int bufferWidth = 21 * buffer.getWidth();
        int bufferHeight = 32 * buffer.getHeight();

        // Compute the content size
        float scaleX = (width - 2f*INNER_MARGIN - 2f*OUTER_MARGIN - 2f*BORDER_THICKNESS) / bufferWidth;
        float scaleY = (height - 2f*INNER_MARGIN - 2f*OUTER_MARGIN - 2f*BORDER_THICKNESS) / bufferHeight;
        float scale = Math.min(scaleX, scaleY);
        int contentWidth = (int) (scale*bufferWidth);
        int contentHeight = (int) (scale*bufferHeight);

        int x = (width - contentWidth) / 2;
        int y = (height - contentHeight) / 2;

        terminalWidget = this.addRenderableWidget(
                new TerminalWidget(buffer, x, y, contentWidth, contentHeight, Component.translatable("component.opencomputers.screen_terminal"))
        );
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderBackground(guiGraphics, i, j, f);

        int contentWidth = terminalWidget.getWidth() + 2*INNER_MARGIN;
        int contentHeight = terminalWidget.getHeight() + 2*INNER_MARGIN;

        int x = (width - contentWidth) / 2;
        int y = (height - contentHeight) / 2;

        // Background
        guiGraphics.fill(RenderPipelines.GUI, x, y, x + contentWidth, y + contentHeight, 0xff000000);

        // Top edge
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.GUI.BORDERS,
                x, y - BORDER_THICKNESS,
                BORDER_THICKNESS*contentWidth, 0, BORDER_THICKNESS/8*contentWidth, BORDER_THICKNESS, BORDER_THICKNESS*2*contentWidth, BORDER_THICKNESS*2
        );

        // Bottom edge
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.GUI.BORDERS,
                x, y + contentHeight,
                BORDER_THICKNESS*contentWidth, BORDER_THICKNESS, BORDER_THICKNESS/8*contentWidth, BORDER_THICKNESS, BORDER_THICKNESS*2*contentWidth, BORDER_THICKNESS*2
        );

        // Left edge
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.GUI.BORDERS,
                x - BORDER_THICKNESS, y,
                BORDER_THICKNESS, BORDER_THICKNESS*contentHeight, BORDER_THICKNESS, BORDER_THICKNESS/8*contentHeight, BORDER_THICKNESS*2, BORDER_THICKNESS*2*contentHeight
        );

        // Right edge
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.GUI.BORDERS,
                x + contentWidth, y,
                BORDER_THICKNESS*contentWidth, BORDER_THICKNESS*contentHeight, BORDER_THICKNESS, BORDER_THICKNESS/8*contentHeight, BORDER_THICKNESS*2, BORDER_THICKNESS*2*contentHeight
        );

        // Top left corner
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.GUI.BORDERS,
                x - BORDER_THICKNESS, y - BORDER_THICKNESS,
                0, 0, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS*2, BORDER_THICKNESS*2);

        // Top right corner
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.GUI.BORDERS,
                x + contentWidth, y - BORDER_THICKNESS,
                BORDER_THICKNESS, 0, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS*2, BORDER_THICKNESS*2);

        // Bottom left corner
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.GUI.BORDERS,
                x - BORDER_THICKNESS, y + contentHeight,
                0, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS*2, BORDER_THICKNESS*2);

        // Bottom right corner
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.GUI.BORDERS,
                x + contentWidth, y + contentHeight,
                BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS*2, BORDER_THICKNESS*2);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
