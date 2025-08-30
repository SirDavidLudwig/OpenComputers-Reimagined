package tech.dlii.opencomputers.client.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.client.Textures;
import tech.dlii.opencomputers.client.gui.ImageButton;
import tech.dlii.opencomputers.common.container.CaseMenu;
import tech.dlii.opencomputers.common.container.ComponentSlot;

public class CaseScreen extends AbstractContainerScreen<CaseMenu> {

    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/background.png");
    public static final ResourceLocation COMPUTER = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/computer.png");

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
                    button -> OpenComputers.LOGGER.info("Power button pressed on tier " + menu.tier + " computer case.")
            )
        );
    }

    protected void drawSecondaryForegroundLayer(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }

    protected void drawInventorySlots(GuiGraphics guiGraphics) {
        for (int i = 0; i < menu.slots.size(); i++) {
            drawSlotInventory(guiGraphics, menu.slots.get(i));
        }
    }

    protected void drawSlotInventory(GuiGraphics guiGraphics, Slot slot) {
        if (slot.container != menu.playerInventory) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Textures.GUI.SLOT, leftPos + slot.x - 1, topPos + slot.y - 1, 0, 0, 18, 18, 18, 18);
        }
        if (slot instanceof ComponentSlot componentSlot) {
            if (!slot.hasItem()) {
                OpenComputers.LOGGER.info("Drawing background for slot " + componentSlot.slot + " of tier " + componentSlot.tier);
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, componentSlot.background, leftPos + slot.x, topPos + slot.y, 0, 0, 16, 16, 16, 16);
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, componentSlot.tierIcon, leftPos + slot.x, topPos + slot.y, 0, 0, 16, 16, 16, 16);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, COMPUTER, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        drawInventorySlots(guiGraphics);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
        super.renderLabels(guiGraphics, i, j);
        drawSecondaryForegroundLayer(guiGraphics, i, j);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        powerButton.toggled = menu.data.get(0) != 0;
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);

        int y = ((height - imageHeight) / 2) + 24;
        guiGraphics.drawCenteredString(font, "Computer Case", width / 2, y, 0x404040);
        guiGraphics.drawString(font, "Test", leftPos + 8, topPos + 72, 0x404040);
    }
}
