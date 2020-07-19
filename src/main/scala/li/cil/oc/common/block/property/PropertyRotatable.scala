package li.cil.oc.common.block.property

import com.google.common.base.{Predicate, Predicates}
import net.minecraft.block.HorizontalBlock
import net.minecraft.state.DirectionProperty
import net.minecraft.util.Direction

import scala.jdk.CollectionConverters._

object PropertyRotatable {
  val Facing = HorizontalBlock.HORIZONTAL_FACING
  val Pitch = DirectionProperty.create("pitch", Set(Direction.DOWN, Direction.UP, Direction.NORTH).asJava)
  val Yaw = DirectionProperty.create("yaw", Direction.Plane.HORIZONTAL)
}
