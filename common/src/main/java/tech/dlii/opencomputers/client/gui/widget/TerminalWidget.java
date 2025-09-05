package tech.dlii.opencomputers.client.gui.widget;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.client.font.Fonts;
import tech.dlii.opencomputers.client.font.Glyph;
import tech.dlii.opencomputers.common.machine.TextBuffer;

import java.util.function.Supplier;

public class TerminalWidget extends AbstractWidget {

    private final TextBuffer buffer;
    private final Supplier<Boolean> hasKeyboard;

    public TerminalWidget(TextBuffer buffer, int x, int y, int width, int height, Supplier<Boolean> hasKeyboard, Component component) {
        super(x, y, width, height, component);
        this.buffer = buffer;
        this.hasKeyboard = hasKeyboard;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {

        // True atlas size
        float atlasWidth = 1024;
        float atlasHeight = 1024;

        // True character cell size
        float cellWidth = 21;
        float cellHeight = 32;

        float contentWidth = this.buffer.getWidth() * cellWidth;
        float contentHeight = this.buffer.getHeight() * cellHeight;

        float scaleX = width / contentWidth;
        float scaleY = height / contentHeight;
        float scale = Math.min(scaleX, scaleY);

        // Desired render size
        int charWidth = Math.round(scale*contentWidth / this.buffer.getWidth());
        int charHeight = Math.round(scale*contentHeight / this.buffer.getHeight());

        for (int row = 0; row < buffer.getHeight(); row++) {
            int x = this.getX();
            int y = this.getY() + charHeight * row;
            for (int col = 0; col < buffer.getWidth(); col++) {
                Fonts.FontStyle style = Fonts.FontStyle.REGULAR;
                Glyph glyph = Fonts.getGlyph(buffer.data.get(row, col), style);

                int uw = Math.round(atlasWidth * scale);
                int vh = Math.round(atlasHeight * scale);

                float u = atlasWidth * glyph.uStart * scale;
                float v = atlasHeight * glyph.vStart * scale;

                guiGraphics.fill(
                        RenderPipelines.GUI,
                        x, y, x + charWidth, y + charHeight, 0xFFFF0000
                );

                guiGraphics.blit(
                        RenderPipelines.GUI_OPAQUE_TEXTURED_BACKGROUND,
                        Fonts.getAtlas(),
                        x, y, u, v, charWidth, charHeight, uw, vh, 0xffffffff
                );

                x += Math.round(glyph.advance * scale);
            }
        }
    }
//
    protected void drawQuad(VertexConsumer buffer, Matrix4f pose, float x0, float y0, float x1, float y1, int color, float u0, float v0, float u1, float v1, int packedLight) {
        addVertex(buffer, pose, x0, y1, color, u0, v1, packedLight);
        addVertex(buffer, pose, x1, y1, color, u1, v1, packedLight);
        addVertex(buffer, pose, x1, y0, color, u1, v0, packedLight);
        addVertex(buffer, pose, x0, y0, color, u0, v0, packedLight);
    }

    protected void addVertex(VertexConsumer buffer, Matrix4f pose, float x, float y, int color, float u, float v, int packedLight) {
        buffer.addVertex(pose, x, y, 0).setColor(color).setUv(u, v).setLight(packedLight);
    }

    @Override
    public void onClick(double d, double e) {
//        this.setFocused(true);
        return;
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        // NO-OP
    }

    @Override
    public void mouseMoved(double d, double e) {
        super.mouseMoved(d, e);
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f, double g) {
        return super.mouseScrolled(d, e, f, g);

    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (!this.isFocused() || !this.hasKeyboard.get()) {
            return false;
        }
        OpenComputers.LOGGER.info("Key pressed:" + i + ", " + j + ", " + k);
        return true;
    }

    @Override
    public @Nullable ComponentPath getCurrentFocusPath() {
        return super.getCurrentFocusPath();
    }

    @Override
    public ScreenRectangle getBorderForArrowNavigation(ScreenDirection screenDirection) {
        return super.getBorderForArrowNavigation(screenDirection);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
