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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.DriverItem;
import tech.dlii.opencomputers.api.machine.Machine;
import tech.dlii.opencomputers.api.machine.MachineHost;
import tech.dlii.opencomputers.api.network.Environment;
import tech.dlii.opencomputers.api.network.EnvironmentHost;
import tech.dlii.opencomputers.api.network.ManagedEnvironment;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.common.block.CaseBlock;
import tech.dlii.opencomputers.common.inventory.CaseMenu;
import tech.dlii.opencomputers.common.inventory.InventorySlots;

import java.util.Optional;

public class CaseBlockEntity extends BaseContainerBlockEntity implements Environment, EnvironmentHost, MachineHost {

    public final int tier;
    private NonNullList<ItemStack> items;
    public final ContainerData dataAccess;

    private NonNullList<Optional<ManagedEnvironment>> components;
    private Machine machine = null;

    public CaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.CASE.get(), blockPos, blockState);
        tier = ((CaseBlock) getBlockState().getBlock()).tier();
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        components = NonNullList.withSize(getContainerSize(), Optional.empty());
        dataAccess = new ContainerData() {
            @Override
            public int get(int i) {
                if (i == 0) {
                    return isRunning() ? 1 : 0;
                }
                return -1;
            }

            @Override
            public void set(int i, int value) {
//                if (i == 0) {
//                    setRunning(value != 0);
//                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
        machine = API.machine.create(this);
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

    public boolean isServer() {
        return getLevel() != null && !getLevel().isClientSide();
    }

    // Component Inventory Methods -------------------------------------------------------------------------------------


    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = super.removeItem(slot, amount);
        if (!stack.isEmpty()) {
            onItemRemoved(slot, stack);
        }
        return stack;
    }

    @Override
    public void setItem(int i, ItemStack stack) {
        super.setItem(i, stack);
        if (!stack.isEmpty()) {
            onItemAdded(i, stack);
        }
    }

    protected void onItemAdded(int slot, ItemStack stack) {
        DriverItem driver = API.driver.driverFor(stack);
        if (driver == null) {
            return;
        }
        ManagedEnvironment environment = driver.createEnvironment(stack, this);
        if (environment == null) {
            return;
        }
        synchronized (this) {
            components.set(slot, Optional.of(environment));
            if (environment.node() != null && this.node() != null) {
                this.node().connect(environment.node());
                OpenComputers.LOGGER.info("Connected " + environment +  " to " + this);
            }
        }
    }

    protected void onItemRemoved(int slot, ItemStack stack) {
        ManagedEnvironment component = components.get(slot).orElse(null);
        if (component == null) {
            return;
        }
        components.set(slot, Optional.empty());
        if (component.node() == null) {
            return;
        }
        component.node().remove();
        OpenComputers.LOGGER.info("Item removed from case", stack);
    }

    // Environment Methods ---------------------------------------------------------------------------------------------

    @Override
    public Node node() {
        if (!isServer()) {
            return null;
        }
        return machine().node();
    }

    // EnvironmentHost Methods -----------------------------------------------------------------------------------------

    @Override
    public Level level() {
        return getLevel();
    }

    @Override
    public double xPosition() {
        return getBlockPos().getX() + 0.5;
    }

    @Override
    public double yPosition() {
        return getBlockPos().getY() + 0.5;
    }

    @Override
    public double zPosition() {
        return getBlockPos().getZ() + 0.5;
    }

    @Override
    public void markChanged() {

    }

    // MachineHost Methods ---------------------------------------------------------------------------------------------


    @Override
    public tech.dlii.opencomputers.api.machine.Machine machine() {
        return this.machine;
    }

    @Override
    public int componentSlot(String address) {
        return 0;
    }

    public boolean isRunning() {
//        return machine.st();
        return false;
    }

    public void tick() {
        machine().tick();
    }


    public static class Ticker<T extends BlockEntity> implements BlockEntityTicker<T> {
        @Override
        public void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
            if (!(blockEntity instanceof CaseBlockEntity caseBlockEntity)) {
                return;
            }
            caseBlockEntity.tick();
        }
    }
}
