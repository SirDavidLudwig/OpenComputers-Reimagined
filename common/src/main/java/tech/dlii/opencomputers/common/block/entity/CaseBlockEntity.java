package tech.dlii.opencomputers.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.common.block.CaseBlock;
import tech.dlii.opencomputers.common.container.CaseMenu;

public class CaseBlockEntity extends BaseContainerBlockEntity {

    public final int tier;
    private NonNullList<ItemStack> items;
    public final ContainerData dataAccess;

    boolean isRunning = false;

    public CaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.CASE.get(), blockPos, blockState);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        tier = ((CaseBlock) getBlockState().getBlock()).tier;
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
    protected Component getDefaultName() {
        return Component.translatable("container.opencomputers.case");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        items = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        OpenComputers.LOGGER.info("Creating menu for tier " + tier);
        return new CaseMenu(containerId, inventory, this, this.dataAccess, tier);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    public void setRunning(boolean running) {
        isRunning = running;
        OpenComputers.LOGGER.info("Powering on");
    }

    public static class Ticker<T extends BlockEntity> implements BlockEntityTicker<T> {
        @Override
        public void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
            OpenComputers.LOGGER.info("Tier " + ((CaseBlockEntity) blockEntity).tier + ": Ticker at " + blockPos.toShortString() + " with state " + blockState.toString() + " and entity type " + blockEntity.getClass().getName());
        }
    }
}
