package li.cil.oc.common.block.property

import net.minecraft.block.HorizontalBlock
import net.minecraft.state.DirectionProperty

object PropertyRotatable {
  val Facing = HorizontalBlock.HORIZONTAL_FACING
  val Pitch = DirectionProperty.create("pitch")
  val Yaw = DirectionProperty.create("yaw")
}
