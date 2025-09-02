package tech.dlii.opencomputers.common.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.common.block.entity.ScreenBlockEntity;
import tech.dlii.opencomputers.common.block.property.BlockStateProperties;

public class ScreenBlock extends BaseEntityBlock {

    public static final MapCodec<ScreenBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.INT.fieldOf("tier").forGetter((screenBlock) -> screenBlock.tier),
                    propertiesCodec()
            ).apply(instance, ScreenBlock::new));
    public static final EnumProperty<Direction> PITCH = BlockStateProperties.PITCH;
    public static final EnumProperty<Direction> YAW = BlockStateProperties.YAW;

    private final int tier;

    public ScreenBlock(int tier, BlockBehaviour.Properties properties) {
        super(properties);
        this.tier = tier;
        registerDefaultState(getStateDefinition().any().setValue(PITCH, Direction.NORTH).setValue(YAW, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return defaultBlockState()
                .setValue(YAW, blockPlaceContext.getHorizontalDirection().getOpposite())
                .setValue(PITCH, switch (blockPlaceContext.getNearestLookingDirection()) {
                    case Direction.UP -> Direction.DOWN;
                    case Direction.DOWN -> Direction.UP;
                    default -> Direction.NORTH;
                });
    }

    public int tier() {
        return this.tier;
    }

    @Override
    protected BlockState rotate(BlockState blockState, Rotation rotation) {
//        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
        return blockState;
    }

    @Override
    protected BlockState mirror(BlockState blockState, Mirror mirror) {
//        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
        return blockState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PITCH, YAW);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ScreenBlockEntity(blockPos, blockState);
    }
}
