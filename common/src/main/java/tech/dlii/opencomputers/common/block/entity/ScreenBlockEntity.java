package tech.dlii.opencomputers.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.common.block.CustomBlockStateProperties;
import tech.dlii.opencomputers.common.block.ScreenBlock;
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
        this.pitch = blockState.getValue(CustomBlockStateProperties.PITCH);
        this.yaw = blockState.getValue(CustomBlockStateProperties.YAW);

        buffer = new TextBuffer(
                Configuration.SCREEN_RESOLUTIONS[tier][0],
                Configuration.SCREEN_RESOLUTIONS[tier][1]
        );
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        // destroy multiblock here
//        if (
//                level.isClientSide()
//                && Minecraft.getInstance().screen instanceof ScreenScreen screen
//                && screen.buffer == buffer) {
//            screen.onClose();
//        }
        OpenComputers.LOGGER.info("Removed screen block");
    }

    public Direction pitch() {
        return this.pitch;
    }

    public Direction yaw() {
        return this.yaw;
    }

    public ScreenBlockEntity origin() {
        return this.origin;
    }

    public TextBuffer buffer() {
        if (origin != this) {
            return origin.buffer();
        }
        return this.buffer;
    }

    public boolean hasKeyboard() {
        if (origin != this) {
            return origin.hasKeyboard();
        }
        // @TODO implement logic
        return true;
    }
}
