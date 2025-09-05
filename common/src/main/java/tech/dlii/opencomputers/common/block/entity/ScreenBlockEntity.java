package tech.dlii.opencomputers.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.dlii.opencomputers.common.block.ScreenBlock;
import tech.dlii.opencomputers.common.block.property.BlockStateProperties;
import tech.dlii.opencomputers.common.machine.TextBuffer;
import tech.dlii.opencomputers.common.config.Configuration;

public class ScreenBlockEntity extends BlockEntity {

    private int tier;
    private Direction pitch;
    private Direction yaw;

    // The tile entity (in a screen cluster) that drives rendering
    private ScreenBlockEntity origin = this;
    public int width = 1;
    public int height = 1;

    public final TextBuffer buffer;

    public ScreenBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.SCREEN.get(), blockPos, blockState);
        this.tier = ((ScreenBlock) getBlockState().getBlock()).tier();
        this.pitch = blockState.getValue(BlockStateProperties.PITCH);
        this.yaw = blockState.getValue(BlockStateProperties.YAW);

        buffer = new TextBuffer(
                Configuration.SCREEN_RESOLUTIONS[tier][0],
                Configuration.SCREEN_RESOLUTIONS[tier][1]
        );
    }

    public Direction pitch() {
        return this.pitch;
    }

    public Direction yaw() {
        return this.yaw;
    }
}
