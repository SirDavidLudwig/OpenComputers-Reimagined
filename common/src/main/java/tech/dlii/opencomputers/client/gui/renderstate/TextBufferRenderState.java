package tech.dlii.opencomputers.client.gui.renderstate;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;
import tech.dlii.opencomputers.client.font.Fonts;
import tech.dlii.opencomputers.client.font.Glyph;
import tech.dlii.opencomputers.common.machine.TextBuffer;

/**
 * This renders the actual text buffer to the screen using GuiGraphics.
 */
public class TextBufferRenderState implements GuiElementRenderState {

    private final Matrix3x2f pose;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    @Nullable
    private final ScreenRectangle scissorArea;
    TextBuffer textBuffer;
    boolean isBackground;

    public TextBufferRenderState(
            Matrix3x2f pose,
            int x,
            int y,
            int width,
            int height,
            @Nullable ScreenRectangle scissorArea,
            TextBuffer textBuffer,
            boolean isBackground
    ) {
        this.pose = pose;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scissorArea = scissorArea;
        this.textBuffer = textBuffer;
        this.isBackground = isBackground;
    }

    @Override
    public void buildVertices(VertexConsumer consumer, float z) {

        float scaleX = width / (textBuffer.getWidth() * 21f);
        float scaleY = height / (textBuffer.getHeight() * 32f);
        float scale = Math.min(scaleX, scaleY);

        float glyphWidth;
        float glyphHeight = 0.0f;
        float y0 = y;
        for (int row = 0; row < textBuffer.getHeight(); row++) {
            float x0 = x;
            for (int col = 0; col < textBuffer.getWidth(); col++) {
                TextBuffer.Character character = textBuffer.get(row, col);
                Glyph glyph = Fonts.getGlyph(character.codePoint, character.fontStyle);
                int color = isBackground ? character.backgroundColor : character.foregroundColor;

                glyphWidth = glyph.width*scale;
                glyphHeight = glyph.height*scale;

                float x1 = x0 + glyphWidth;
                float y1 = y0 + glyphHeight;

                consumer.addVertexWith2DPose(this.pose, x0,  y0,  z).setUv(glyph.uStart, glyph.vStart).setColor(color);
                consumer.addVertexWith2DPose(this.pose, x0,  y1, z).setUv(glyph.uStart, glyph.vEnd).setColor(color);
                consumer.addVertexWith2DPose(this.pose, x1, y1, z).setUv(glyph.uEnd, glyph.vEnd).setColor(color);
                consumer.addVertexWith2DPose(this.pose, x1, y0,  z).setUv(glyph.uEnd, glyph.vStart).setColor(color);

                x0 += glyph.advance*scale;
            }
            y0 += glyphHeight;
        }
    }

    @Override
    public RenderPipeline pipeline() {
        return isBackground ? RenderPipelines.GUI : RenderPipelines.GUI_OPAQUE_TEXTURED_BACKGROUND;
    }

    @Override
    public TextureSetup textureSetup() {
        if (isBackground) {
            return TextureSetup.noTexture();
        }
        GpuTextureView texture = Minecraft.getInstance().getTextureManager().getTexture(Fonts.getAtlas()).getTextureView();
        return TextureSetup.singleTexture(texture);
    }

    @Override
    public @Nullable ScreenRectangle scissorArea() {
        return this.scissorArea;
    }

    @Override
    public @Nullable ScreenRectangle bounds() {
        ScreenRectangle rectangle = new ScreenRectangle(x, y, width, height);
        rectangle = rectangle.transformMaxBounds(this.pose);
        return this.scissorArea != null ? this.scissorArea.intersection(rectangle) : rectangle;
    }
}
