package tech.dlii.opencomputers.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tech.dlii.opencomputers.OpenComputers;

public class CaseBlockEntity extends BlockEntity {
    public CaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.CASE.get(), blockPos, blockState);
    }

    public static class Ticker<T extends BlockEntity> implements BlockEntityTicker<T> {
        @Override
        public void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
            OpenComputers.LOGGER.info("Ticker at " + blockPos.toShortString() + " with state " + blockState.toString() + " and entity type " + blockEntity.getClass().getName());
        }
    }
}
