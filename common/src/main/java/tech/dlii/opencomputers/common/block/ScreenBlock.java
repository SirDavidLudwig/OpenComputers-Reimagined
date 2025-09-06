package tech.dlii.opencomputers.common.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.client.gui.screen.ScreenScreen;
import tech.dlii.opencomputers.common.block.entity.ScreenBlockEntity;

public class ScreenBlock extends BaseEntityBlock implements InteractionEvent.RightClickBlock {

    public static final MapCodec<ScreenBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.INT.fieldOf("tier").forGetter((screenBlock) -> screenBlock.tier),
                    propertiesCodec()
            ).apply(instance, ScreenBlock::new));
    public static final EnumProperty<Direction> PITCH = CustomBlockStateProperties.PITCH;
    public static final EnumProperty<Direction> YAW = CustomBlockStateProperties.YAW;

    private final int tier;

    public ScreenBlock(int tier, BlockBehaviour.Properties properties) {
        super(properties);
        this.tier = tier;
        registerDefaultState(getStateDefinition().any().setValue(PITCH, Direction.NORTH).setValue(YAW, Direction.NORTH));
        InteractionEvent.RIGHT_CLICK_BLOCK.register(this);
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
    public InteractionResult click(Player player, InteractionHand hand, BlockPos pos, Direction face) {
        if (player.level().getBlockEntity(pos) == null || !(player.level().getBlockEntity(pos) instanceof ScreenBlockEntity blockEntity)) {
            return InteractionResult.PASS;
        }
        if (player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }
        if (player.level().isClientSide()) {
            openGui(blockEntity);
        }
        return InteractionResult.SUCCESS;
    }

    public void openGui(ScreenBlockEntity blockEntity) {
        Minecraft.getInstance().setScreen(
                new ScreenScreen(
                        blockEntity.origin().buffer(),
                        tier,
                        () -> blockEntity.origin().hasKeyboard()
                )
        );
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
