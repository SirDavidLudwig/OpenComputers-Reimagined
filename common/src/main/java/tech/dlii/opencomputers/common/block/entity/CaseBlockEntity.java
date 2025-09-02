package tech.dlii.opencomputers.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.common.block.CaseBlock;
import tech.dlii.opencomputers.common.inventory.CaseMenu;
import tech.dlii.opencomputers.common.inventory.InventorySlots;

public class CaseBlockEntity extends BaseContainerBlockEntity {

    public final int tier;
    private NonNullList<ItemStack> items;
    public final ContainerData dataAccess;

    boolean isRunning = false;

    public CaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.CASE.get(), blockPos, blockState);
        tier = ((CaseBlock) getBlockState().getBlock()).tier();
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        dataAccess = new ContainerData() {
            @Override
            public int get(int i) {
                if (i == 0) {
                    return isRunning ? 1 : 0;
                }
                return -1;
            }

            @Override
            public void set(int i, int value) {
                if (i == 0) {
                    setRunning(value != 0);
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.opencomputers.case");
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        items = nonNullList;
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        ContainerHelper.loadAllItems(valueInput, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        ContainerHelper.saveAllItems(valueOutput, this.items);
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new CaseMenu(containerId, inventory, this, this.dataAccess, tier);
    }

    @Override
    public boolean canOpen(Player player) {
        return super.canOpen(player) && (tier != Tier.FOUR || player.isCreative());
    }

    @Override
    public int getContainerSize() {
        return InventorySlots.CASE.get(tier).size();
    }

    public void setRunning(boolean running) {
        isRunning = running;
        OpenComputers.LOGGER.info("Powering on");
    }

//    public static class Ticker<T extends BlockEntity> implements BlockEntityTicker<T> {
//        @Override
//        public void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
//            OpenComputers.LOGGER.info("Tier " + ((CaseBlockEntity) blockEntity).tier + ": Ticker at " + blockPos.toShortString() + " with state " + blockState.toString() + " and entity type " + blockEntity.getClass().getName());
//        }
//    }

    // Machine Stuff

    public void updateComponents() {

    }

    public void connectComponents() {
        // Loop over container slots
        // If the slot is a component slot, not empty, and it has not yet been added as a component,
        // Create the environment
    }

    public void disconnectComponents() {
        // Loop through registered components and remove the nodes.
    }
}
