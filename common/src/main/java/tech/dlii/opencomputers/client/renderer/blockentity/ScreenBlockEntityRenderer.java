package tech.dlii.opencomputers.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import tech.dlii.opencomputers.client.renderer.CustomRenderTypes;
import tech.dlii.opencomputers.client.font.Fonts;
import tech.dlii.opencomputers.client.font.Glyph;
import tech.dlii.opencomputers.common.block.entity.ScreenBlockEntity;

public class ScreenBlockEntityRenderer implements BlockEntityRenderer<ScreenBlockEntity> {

    Font font;

    public ScreenBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(ScreenBlockEntity screen, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        poseStack.pushPose();
        transform(screen, poseStack);
        float scaleFactor = 0.001f;
        poseStack.scale(scaleFactor, scaleFactor, scaleFactor);

        // Set some test text
        screen.buffer.data.set(0, 0, "Hello World! ☺", false);

        float left = 0.0f;
        float top = 0.0f;
        int light = 15728640;
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < screen.buffer.getWidth(); index++) {
            builder.appendCodePoint(screen.buffer.data.get(0, index));
        }

        poseStack.translate(100, 100, 0);

        VertexConsumer buffer = multiBufferSource.getBuffer(CustomRenderTypes.screenText(Fonts.getAtlas()));

        float CHAR_HEIGHT = 32;

        for (int row = 0; row < screen.buffer.getHeight(); row++) {
            for (int col = 0; col < screen.buffer.getWidth(); col++) {
                int character = screen.buffer.data.get(row, col);
                Fonts.FontStyle style = Fonts.FontStyle.REGULAR;
                Glyph glyph = Fonts.getGlyph(character, style);

                buffer.addVertex(poseStack.last(), left, CHAR_HEIGHT, 0).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(glyph.uStart, glyph.vEnd).setLight(15728880);
                buffer.addVertex(poseStack.last(), left + glyph.width, CHAR_HEIGHT, 0).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(glyph.uEnd, glyph.vEnd).setLight(15728880);
                buffer.addVertex(poseStack.last(), left + glyph.width, 0, 0).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(glyph.uEnd, glyph.vStart).setLight(15728880);
                buffer.addVertex(poseStack.last(), left, 0, 0).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(glyph.uStart, glyph.vStart).setLight(15728880);
                left += glyph.width;
            }
            break;
        }
//        font.drawInBatch(builder.toString(), left, top, color, false, poseStack.last().pose(), multiBufferSource, Font.DisplayMode.POLYGON_OFFSET, 0, light);
        poseStack.popPose();
    }

    protected void transform(ScreenBlockEntity screen, PoseStack poseStack) {
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(180)); // Flip upright
        poseStack.mulPose(Axis.YP.rotationDegrees(screen.yaw().toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0f - ((screen.pitch() == Direction.DOWN) ? 90.f : screen.pitch().toYRot())));
        poseStack.translate(-0.5, -0.5, -0.5);
    }
}
