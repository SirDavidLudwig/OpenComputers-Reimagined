package tech.dlii.opencomputers.client.gui.widget;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.common.machine.TextBuffer;

import java.util.function.Supplier;

public class TerminalWidget extends AbstractWidget {

    public final TextBuffer textBuffer;
    public final Supplier<Boolean> hasKeyboard;

    public TerminalWidget(TextBuffer textBuffer, int x, int y, int width, int height, Supplier<Boolean> hasKeyboard, Component component) {
        super(x, y, width, height, component);
        this.textBuffer = textBuffer;
        this.hasKeyboard = hasKeyboard;
    }

    // Platform-specific implementation
    @ExpectPlatform
    public static void drawBuffer(
            TerminalWidget instance,
            GuiGraphics guiGraphics,
            TextBuffer textBuffer,
            int x,
            int y,
            int width,
            int height
    ) {
        throw new AssertionError();
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        drawBuffer(this, guiGraphics, textBuffer, getX(), getY(), getWidth(), getHeight());
    }

    protected void drawQuad(VertexConsumer buffer, Matrix3x2f pose, float x0, float y0, float x1, float y1, int color, float u0, float v0, float u1, float v1, int packedLight) {
        addVertex(buffer, pose, x0, y1, color, u0, v1, packedLight);
        addVertex(buffer, pose, x1, y1, color, u1, v1, packedLight);
        addVertex(buffer, pose, x1, y0, color, u1, v0, packedLight);
        addVertex(buffer, pose, x0, y0, color, u0, v0, packedLight);
    }

    protected void addVertex(VertexConsumer buffer, Matrix3x2f pose, float x, float y, int color, float u, float v, int packedLight) {
        buffer.addVertexWith2DPose(pose, x, y, -1.0f).setColor(color).setUv(u, v).setLight(packedLight).setNormal(0.0f, 1.0f, 0.0f).setUv1(0, 0).setUv2(0, 0);
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
