package tech.dlii.opencomputers.common.block.property;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class BlockStateProperties {
    public static final EnumProperty<Direction> PITCH = EnumProperty.create("pitch", Direction.class, (direction -> switch (direction) {
        case Direction.UP, Direction.DOWN, Direction.NORTH -> true;
        default -> false;
    }));
    public static final EnumProperty<Direction> YAW = EnumProperty.create("yaw", Direction.class, Direction.Plane.HORIZONTAL);
}
