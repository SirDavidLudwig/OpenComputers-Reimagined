package tech.dlii.opencomputers.client.gui.screen;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import tech.dlii.opencomputers.client.Textures;
import tech.dlii.opencomputers.client.gui.widget.ImageButton;
import tech.dlii.opencomputers.common.network.serverbound.ToggleComputerPowerPayload;
import tech.dlii.opencomputers.common.inventory.CaseMenu;

public class CaseScreen extends AbstractDynamicContainerScreen<CaseMenu> {

    public ImageButton powerButton;

    public CaseScreen(CaseMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        powerButton = addRenderableWidget(
            new ImageButton(
                    leftPos + 70,
                    topPos + 33,
                    18,
                    18,
                    true,
                    Textures.GUI.POWER_BUTTON,
                    Component.translatable("component.opencomputers.power_button"),
                    button -> {
                        NetworkManager.sendToServer(new ToggleComputerPowerPayload(menu.containerId, !powerButton.toggled));
                    }
            )
        );
    }

    @Override
    protected void drawSecondaryBackgroundLayer(GuiGraphics guiGraphics) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Textures.GUI.COMPUTER, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    protected void drawSecondaryForegroundLayer(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Draw power button tooltip here
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        powerButton.toggled = menu.data.get(0) != 0;
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
