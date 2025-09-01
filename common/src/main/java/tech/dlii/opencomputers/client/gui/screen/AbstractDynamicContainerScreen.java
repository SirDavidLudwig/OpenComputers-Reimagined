package tech.dlii.opencomputers.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import tech.dlii.opencomputers.client.Textures;
import tech.dlii.opencomputers.common.inventory.AbstractBaseContainerMenu;
import tech.dlii.opencomputers.common.inventory.ComponentSlot;

public abstract class AbstractDynamicContainerScreen<T extends AbstractBaseContainerMenu> extends AbstractContainerScreen<T> {

    public AbstractDynamicContainerScreen(T abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    protected void drawSecondaryBackgroundLayer(GuiGraphics guiGraphics) {
        // NO-OP
    }

    protected void drawSecondaryForegroundLayer(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // NO-OP
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
                if (componentSlot.background != null) {
                    guiGraphics.blit(RenderPipelines.GUI_TEXTURED, componentSlot.background, leftPos + slot.x, topPos + slot.y, 0, 0, 16, 16, 16, 16);
                }
                if (componentSlot.tierIcon != null) {
                    guiGraphics.blit(RenderPipelines.GUI_TEXTURED, componentSlot.tierIcon, leftPos + slot.x, topPos + slot.y, 0, 0, 16, 16, 16, 16);
                }
            }
        }
    }

    protected void drawSlotHighlight(GuiGraphics guiGraphics, Slot slot) {
        // If the player is currently moving an item, don't do any highlighting.
        if (!minecraft.player.containerMenu.getCarried().isEmpty()) {
            return;
        }
        // If nothing is hovered, nothing to highlight.
        if (hoveredSlot == null) {
            return;
        }
        // Hovering over a slot, highlight any items that can go here
        if (hoveredSlot instanceof ComponentSlot componentSlot) {
            if (componentSlot.hasItem()) {
                return;
            }
            if (isInPlayerInventory(componentSlot)) {
                return;
            }
            if (!hoveredSlot.mayPlace(slot.getItem())) {
                return;
            }
        }
        // Hovering over an item in player inventory, highlight any slots that the item can fit in
        else if (isInPlayerInventory(hoveredSlot)) {
            if (!hoveredSlot.hasItem()) {
                return;
            }
            if (slot.hasItem()) {
                return;
            }
            if (isInPlayerInventory(slot)) {
                return;
            }
            if (!slot.mayPlace(hoveredSlot.getItem())) {
                return;
            }
        }
        guiGraphics.fill(RenderPipelines.GUI, slot.x, slot.y, slot.x + 16, slot.y + 16, 0x80FFFFFF);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Textures.GUI.BACKGROUND, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        drawSecondaryBackgroundLayer(guiGraphics);
        drawInventorySlots(guiGraphics);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(leftPos, topPos);
        drawSecondaryForegroundLayer(guiGraphics, mouseX, mouseY);
        for (Slot slot : menu.slots) {
            drawSlotHighlight(guiGraphics, slot);
        }
        guiGraphics.pose().popMatrix();
        renderTooltip(guiGraphics, mouseX, mouseY);

    }

    protected boolean isInPlayerInventory(Slot slot) {
        return slot.container == menu.playerInventory;
    }

}
