package tech.dlii.opencomputers.common.inventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;

import java.util.List;

public class CaseMenu extends AbstractBaseContainerMenu {
    public final ContainerData data;
    public final int tier;

    // Client-side Creation
    public static CaseMenu create(int containerId, Inventory playerInventory, final FriendlyByteBuf data) {
        final int tier = data.readInt();
        return new CaseMenu(containerId, playerInventory, new SimpleContainer(InventorySlots.CASE.get(tier).size()), new SimpleContainerData(1), tier);
    }

    /*@Override
    public ItemStack quickMoveStack(Player player, int fromIndex)
    {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(fromIndex);

        if (slot.hasItem()) {

            ItemStack oriStack = slot.getItem();
            newStack = oriStack.copy();



            if (fromIndex < this.playerInventory.getContainerSize()) {

                if (!this.moveItemStackTo(oriStack, this.playerInventory.getContainerSize(), this.slots.size(), true))
                    return ItemStack.EMPTY;

            } else if (!this.moveItemStackTo(oriStack, 0, this.playerInventory.getContainerSize(), false)) {

                return ItemStack.EMPTY;
            }

            if (oriStack.isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();

        }

        return newStack;
    }*/

    /*@Override
    public ItemStack quickMoveStack(Player player, int fromIndex) {

        ItemStack newStack = ItemStack.EMPTY;// Start with an empty stack
        Slot slot = this.slots.get(fromIndex);

        if (slot.hasItem()) {

            ItemStack oriStack = slot.getItem();
            newStack = oriStack.copy();

            InventorySlots inventorySlots = InventorySlots.CASE.get(tier);
            InventorySlots.InventorySlot componentSlot = inventorySlots.get(fromIndex);

            if (fromIndex < this.playerInventory.getContainerSize()) {
                if (!this.moveItemStackTo(oriStack, fromIndex, componentSlot.index(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(oriStack, componentSlot.index(), 0, false)) {

                return ItemStack.EMPTY;
            }

            // Set it to empty if transferred the whole stack or mark it as changed.
            if (oriStack.isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
        }


        return newStack;
        //return super.quickMoveStack(player, i);
    }*/

    @Override
    public ItemStack quickMoveStack(Player player, int fromIndex) {

        //ItemStack newStack = ItemStack.EMPTY;// Start with an empty stack
        Slot slot = this.slots.get(fromIndex);

        if (slot.hasItem()) {

            ItemStack oriStack = slot.getItem();

            if (slot instanceof ComponentSlot) {// Computer -> Player inv

                if (!this.moveItemStackTo(oriStack, fromIndex, this.playerInventory.getContainerSize(), true))
                    return ItemStack.EMPTY;
                slot.set(ItemStack.EMPTY);

            } else {// Player inv -> Computer

                /*ItemStack newStack = oriStack.copy();

                InventorySlots inventorySlots = InventorySlots.CASE.get(tier);




                // Set it to empty if transferred the whole stack or mark it as changed.
                if (oriStack.isEmpty())
                    slot.set(ItemStack.EMPTY);
                else
                    slot.setChanged();

                return newStack;*/
            }
        }
        return ItemStack.EMPTY;
    }

    // Server-side Constructor
    public CaseMenu(int containerId, Inventory playerInventory, Container container, ContainerData containerData, int tier) {
        super(MenuTypes.CASE.get(), containerId, playerInventory, container);

        InventorySlots inventorySlots = InventorySlots.CASE.get(tier);

        checkContainerSize(container, inventorySlots.size());
        checkContainerDataCount(containerData, 1);

        this.data = containerData;
        this.tier = tier;

        InventorySlots.InventorySlot slot;
        List<InventorySlots.InventorySlot> slots;

        // EEPROM
        slot = inventorySlots.get(SlotType.EEPROM).getFirst();
        this.addSlot(new ComponentSlot(container, slot.index(), 48, 34, slot.type(), slot.tier()));

        // Cards
        slots = inventorySlots.get(SlotType.CARD);
        for (int i = 0; i < slots.size(); i++) {
            slot = slots.get(i);
            this.addSlot(new ComponentSlot(container, slot.index(), 98, 16 + i*18, slot.type(), slot.tier()));
        }

        // CPU slot
        slot = inventorySlots.get(SlotType.CPU).getFirst();
        this.addSlot(new ComponentSlot(container, slot.index(), 120, 16, slot.type(), slot.tier()));

        // Memory Slots
        slots = inventorySlots.get(SlotType.MEMORY);
        for (int i = 0; i < slots.size(); i++) {
            slot = slots.get(i);
            this.addSlot(new ComponentSlot(container, slot.index(), 120, 16 + (i+1)*18, slot.type(), slot.tier()));
        }

        // Drives
        slots = inventorySlots.get(SlotType.STORAGE);
        for (int i = 0; i < slots.size(); i++) {
            slot = slots.get(i);
            this.addSlot(new ComponentSlot(container, slot.index(), 142, 16 +  i*18, slot.type(), slot.tier()));
        }

        // Floppy drive
        slots = inventorySlots.get(SlotType.FLOPPY);
        for (int i = 0; i < slots.size(); i++) {
            slot = slots.get(i);
            this.addSlot(new ComponentSlot(container, slot.index(), 142, 16 + (i+inventorySlots.get(SlotType.STORAGE).size())*18, slot.type(), slot.tier()));
        }

        bindInventory(playerInventory);

        this.addDataSlots(containerData);
    }

    protected void bindInventory(Inventory playerInventory)
    {
        // Add player inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        // Add player hotbar slots
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }


    @Override
    public boolean stillValid(Player player) {
        // Prevent interacting with creative cases while not in creative mode
        return super.stillValid(player) && (tier != Tier.FOUR || player.isCreative());    }
}
