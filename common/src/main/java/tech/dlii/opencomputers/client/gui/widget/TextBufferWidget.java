package tech.dlii.opencomputers.client.gui.widget;

import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.client.gui.ExtendedGuiGraphics;
import tech.dlii.opencomputers.client.gui.renderstate.TextBufferRenderState;
import tech.dlii.opencomputers.common.machine.component.TextBufferComponent;

import java.util.function.Supplier;

public class TextBufferWidget extends AbstractWidget {

    public final TextBufferComponent textBuffer;
    public final Supplier<Boolean> hasKeyboard;

    public TextBufferWidget(TextBufferComponent textBuffer, int x, int y, int width, int height, Supplier<Boolean> hasKeyboard, Component component) {
        super(x, y, width, height, component);
        this.textBuffer = textBuffer;
        this.hasKeyboard = hasKeyboard;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        draw(guiGraphics, true);
        draw(guiGraphics, false);
    }

    protected void draw(GuiGraphics guiGraphics, boolean background) {
        ExtendedGuiGraphics.submitGuiElement(guiGraphics, new TextBufferRenderState(
                guiGraphics.pose(),
                getX(),
                getY(),
                getWidth(),
                getHeight(),
                ExtendedGuiGraphics.peekScissorArea(guiGraphics),
                textBuffer,
                background
        ));
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
