package tech.dlii.opencomputers.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.dlii.opencomputers.common.block.ScreenBlock;

public class ScreenBlockEntity extends BlockEntity {

    private int tier;

    public ScreenBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.SCREEN.get(), blockPos, blockState);
        this.tier = ((ScreenBlock) getBlockState().getBlock()).tier();
    }
}
