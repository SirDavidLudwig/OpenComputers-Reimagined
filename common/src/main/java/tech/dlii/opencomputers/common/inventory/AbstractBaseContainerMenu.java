package tech.dlii.opencomputers.common.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AbstractBaseContainerMenu extends AbstractContainerMenu {
    public final Inventory playerInventory;
    public final Container container;

    public AbstractBaseContainerMenu(@Nullable MenuType<?> menuType, int containerId, Inventory playerInventory, Container container) {
        super(menuType, containerId);
        this.playerInventory = playerInventory;
        this.container = container;
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
