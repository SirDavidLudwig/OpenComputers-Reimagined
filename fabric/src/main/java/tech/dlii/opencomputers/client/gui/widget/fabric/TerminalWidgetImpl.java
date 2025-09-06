package tech.dlii.opencomputers.client.gui.widget.fabric;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.client.font.Fonts;
import tech.dlii.opencomputers.client.font.Glyph;
import tech.dlii.opencomputers.client.gui.widget.TerminalWidget;
import tech.dlii.opencomputers.common.machine.TextBuffer;

public class TerminalWidgetImpl {

    public static long NEXT_LOG_TIME = 0;

    public static void drawBuffer(
            TerminalWidget instance,
            GuiGraphics guiGraphics,
            TextBuffer textBuffer,
            int x,
            int y,
            int width,
            int height
    ) {
        guiGraphics.guiRenderState.submitGuiElement(new GuiElementRenderState() {

            private final Matrix3x2f pose = new Matrix3x2f(guiGraphics.pose());
            @Nullable
            private final ScreenRectangle scissorArea = guiGraphics.scissorStack.peek();

            @Override
            public void buildVertices(VertexConsumer consumer, float z) {

                float scale = width / (textBuffer.getWidth()*21f);

                for (int row = 0; row < textBuffer.getHeight(); row++) {
                    float x0 = x;
                    float y0 = y + (row*32*scale);
                    for (int col = 0; col < textBuffer.getWidth(); col++) {
                        Fonts.FontStyle style = Fonts.FontStyle.REGULAR;
                        Glyph glyph = Fonts.getGlyph(textBuffer.data.get(row, col), style);

                        float x1 = x0 + glyph.width*scale;
                        float y1 = y0 + glyph.height*scale;

                        consumer.addVertexWith2DPose(this.pose, x0,  y0,  z).setUv(glyph.uStart, glyph.vStart).setColor(0xFFFF0000);
                        consumer.addVertexWith2DPose(this.pose, x0,  y1, z).setUv(glyph.uStart, glyph.vEnd).setColor(0xFFFF0000);
                        consumer.addVertexWith2DPose(this.pose, x1, y1, z).setUv(glyph.uEnd, glyph.vEnd).setColor(0xFFFF0000);
                        consumer.addVertexWith2DPose(this.pose, x1, y0,  z).setUv(glyph.uEnd, glyph.vStart).setColor(0xFFFF0000);

                        x0 += glyph.advance * scale;
                    }
                }
            }

            @Override
            public RenderPipeline pipeline() {
                return RenderPipelines.GUI;
            }

            @Override
            public TextureSetup textureSetup() {
                return TextureSetup.noTexture();
            }

            @Override
            public @Nullable ScreenRectangle scissorArea() {
                return this.scissorArea;
            }

            @Override
            public @Nullable ScreenRectangle bounds() {
                ScreenRectangle rectangle = new ScreenRectangle(0, 0, 10, 10);
                rectangle = rectangle.transformMaxBounds(this.pose);
                return this.scissorArea != null ? this.scissorArea.intersection(rectangle) : rectangle;
            }
        });
        guiGraphics.guiRenderState.submitGuiElement(new GuiElementRenderState() {

            private final Matrix3x2f pose = new Matrix3x2f(guiGraphics.pose());
            @Nullable
            private final ScreenRectangle scissorArea = guiGraphics.scissorStack.peek();

            @Override
            public void buildVertices(VertexConsumer consumer, float z) {

                float scale = width / (textBuffer.getWidth()*21f);

                for (int row = 0; row < textBuffer.getHeight(); row++) {
                    float x0 = x;
                    float y0 = y + (row*32*scale);
                    for (int col = 0; col < textBuffer.getWidth(); col++) {
                        Fonts.FontStyle style = Fonts.FontStyle.REGULAR;
                        int cp = textBuffer.data.get(row, col);
                        Glyph glyph = Fonts.getGlyph(cp, style);

                        float x1 = x0 + glyph.width*scale;
                        float y1 = y0 + glyph.height*scale;

                        consumer.addVertexWith2DPose(this.pose, x0,  y0,  z).setUv(glyph.uStart, glyph.vStart).setColor(0xFFFFFFFF);
                        consumer.addVertexWith2DPose(this.pose, x0,  y1, z).setUv(glyph.uStart, glyph.vEnd).setColor(0xFFFFFFFF);
                        consumer.addVertexWith2DPose(this.pose, x1, y1, z).setUv(glyph.uEnd, glyph.vEnd).setColor(0xFFFFFFFF);
                        consumer.addVertexWith2DPose(this.pose, x1, y0,  z).setUv(glyph.uEnd, glyph.vStart).setColor(0xFFFFFFFF);

                        x0 += glyph.advance * scale;
                    }
                }
            }

            @Override
            public RenderPipeline pipeline() {
                return RenderPipelines.GUI_OPAQUE_TEXTURED_BACKGROUND;
            }

            @Override
            public TextureSetup textureSetup() {
                GpuTextureView texture = Minecraft.getInstance().getTextureManager().getTexture(Fonts.getAtlas()).getTextureView();
                return TextureSetup.singleTexture(texture);
            }

            @Override
            public @Nullable ScreenRectangle scissorArea() {
                return this.scissorArea;
            }

            @Override
            public @Nullable ScreenRectangle bounds() {
                ScreenRectangle rectangle = new ScreenRectangle(0, 0, 10, 10);
                rectangle = rectangle.transformMaxBounds(this.pose);
                return this.scissorArea != null ? this.scissorArea.intersection(rectangle) : rectangle;
            }
        });
    }
}
