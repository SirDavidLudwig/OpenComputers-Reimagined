package tech.dlii.opencomputers.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import tech.dlii.opencomputers.client.renderer.CustomRenderTypes;
import tech.dlii.opencomputers.client.font.Fonts;
import tech.dlii.opencomputers.client.font.Glyph;
import tech.dlii.opencomputers.common.block.entity.ScreenBlockEntity;
import tech.dlii.opencomputers.common.machine.TextBuffer;

import java.util.function.Function;
import java.util.function.Supplier;

public class ScreenBlockEntityRenderer implements BlockEntityRenderer<ScreenBlockEntity> {

    Font font;

    // @TODO should be computed from font
    float CHAR_CELL_WIDTH = 21;
    float CHAR_CELL_HEIGHT = 32;

    public ScreenBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(ScreenBlockEntity screen, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        if (!screen.isOrigin()) {
            return;
        }
        poseStack.pushPose();
        transform(screen, poseStack);

        // @TODO Remove later
        // Insert some test data into the buffer.
        for (int i = 0; i < screen.buffer.getWidth(); i++) {
            screen.buffer.set(0, i, Integer.toString(i % 10), false);
        }

        // Background
        draw(
                multiBufferSource.getBuffer(CustomRenderTypes.screenTextBackground()),
                poseStack.last().pose(),
                screen.buffer,
                (character) -> character.backgroundColor
        );
        // Foreground
        draw(
                multiBufferSource.getBuffer(CustomRenderTypes.screenText(Fonts.getAtlas())),
                poseStack.last().pose(),
                screen.buffer,
                (character) -> character.foregroundColor
        );
        poseStack.popPose();
    }

    protected void transform(ScreenBlockEntity screen, PoseStack poseStack) {
        // Orient to the correct side
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(180)); // Flip upright
        poseStack.mulPose(Axis.YP.rotationDegrees(screen.yaw().toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0f - ((screen.pitch() == Direction.DOWN) ? 90.f : screen.pitch().toYRot())));
        poseStack.translate(-0.5, -0.5, -0.5);

        // Move off of border (1 pixel)
        poseStack.translate(2/16f, 2/16f, 0);

        // Screen geometry
        float screenWidth = 1.0f;
        float screenHeight = 1.0f;
        float innerWidth = screenWidth - 4/16f;
        float innerHeight = screenHeight - 4/16f;

        // Buffer render size
        int bufferWidth = screen.buffer.getWidth();
        int bufferHeight = screen.buffer.getHeight();
        float contentWidth = bufferWidth * CHAR_CELL_WIDTH;
        float contentHeight = bufferHeight * CHAR_CELL_HEIGHT;

        float scaleX = innerWidth / contentWidth;
        float scaleY = innerHeight / contentHeight;
        float scale = Math.min(scaleX, scaleY);
        float translateX = (innerWidth - contentWidth * scale) * 0.5f;
        float translateY = (innerHeight - contentHeight * scale) * 0.5f;

        poseStack.translate(translateX, translateY, 0);
        poseStack.scale(scale, scale, 1f);
    }

    protected void draw(VertexConsumer buffer, Matrix4f pose, TextBuffer textBuffer, Function<TextBuffer.Character, Integer> getColor) {
        float CHAR_HEIGHT = 32;
        float x0, y0, x1, y1;
        for (int row = 0; row < textBuffer.getHeight(); row++) {
            x0 = 0.0f;
            y0 = row * CHAR_HEIGHT;
            for (int col = 0; col < textBuffer.getWidth(); col++) {
                TextBuffer.Character character = textBuffer.get(row, col);
                Glyph glyph = Fonts.getGlyph(character.codePoint, character.fontStyle);

                x1 = x0 + glyph.width;
                y1 = y0 + glyph.height;

                drawQuad(
                        buffer,
                        pose,
                        x0, y0, x1, y1,
                        getColor.apply(character),
                        glyph.uStart, glyph.vStart, glyph.uEnd, glyph.vEnd,
                        15728880
                );

                x0 += glyph.advance;
            }
        }
    }

    protected void drawQuad(VertexConsumer buffer, Matrix4f pose, float x0, float y0, float x1, float y1, int color, float u0, float v0, float u1, float v1, int packedLight) {
        addVertex(buffer, pose, x0, y1, color, u0, v1, packedLight);
        addVertex(buffer, pose, x1, y1, color, u1, v1, packedLight);
        addVertex(buffer, pose, x1, y0, color, u1, v0, packedLight);
        addVertex(buffer, pose, x0, y0, color, u0, v0, packedLight);
    }

    protected void addVertex(VertexConsumer buffer, Matrix4f pose, float x, float y, int color, float u, float v, int packedLight) {
        buffer.addVertex(pose, x, y, 0).setColor(color).setUv(u, v).setLight(packedLight);
    }
}
