package tech.dlii.opencomputers.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.api.Tier;

public class CaseMenu extends AbstractContainerMenu {

    public static int SLOT_SIZE = 18;

    public final Inventory playerInventory;
    public final Container container;
    public final ContainerData data;
    public final int tier;

    // Client-side Creation
    public static CaseMenu create(int containerId, Inventory playerInventory, final FriendlyByteBuf data) {
        final int tier = data.readInt();
        return new CaseMenu(containerId, playerInventory, new SimpleContainer(4), new SimpleContainerData(1), tier);
    }

    // Server-side Constructor
    public CaseMenu(int containerId, Inventory playerInventory, Container container, ContainerData containerData, int tier) {
        super(MenuTypes.CASE.get(), containerId);

        checkContainerSize(container, 1);
        checkContainerDataCount(containerData, 1);

        this.container = container;
        this.playerInventory = playerInventory;
        this.data = containerData;
        this.tier = tier;



        // Add CPU slot
        this.addSlot(new ComponentSlot(container, 0, 120, 16, tech.dlii.opencomputers.api.driver.item.Slot.CPU, Tier.ONE));

//        InventorySlots.InventorySlot slot;
//        for (int i = 0; i < (tier >= Tier.THREE ? 2 : 1); i++) {
//            slot = InventorySlots.computer[tier][getItems().size()];
//            addSlot(98, 16 + i * SLOT_SIZE, )
//        }

//        this.addSlot(new Slot(container, 0, 62, 17)); // CPU
//        this.addSlot(new Slot(container, 1, 62, 53)); // RAM
//        this.addSlot(new Slot(container, 2, 116, 17)); // HDD
//        this.addSlot(new Slot(container, 3, 116, 53)); // GPU

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

        this.addDataSlots(containerData);
    }



    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
