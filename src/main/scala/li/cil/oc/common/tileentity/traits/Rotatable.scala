package li.cil.oc.common.tileentity.traits

import li.cil.oc.api.internal
import li.cil.oc.common.block.property.PropertyRotatable
import li.cil.oc.util.ExtendedDirection._
import li.cil.oc.util.ExtendedWorld._
import li.cil.oc.util.RotationHelper
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.state.DirectionProperty
import net.minecraft.util.Direction

import scala.jdk.CollectionConverters._

trait Rotatable extends RotationAware with internal.Rotatable {
  // ----------------------------------------------------------------------- //
  // Lookup tables
  // ----------------------------------------------------------------------- //

  private val pitch2Direction = Array(Direction.UP, Direction.NORTH, Direction.DOWN)

  private val yaw2Direction = Array(Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST)

  // ----------------------------------------------------------------------- //
  // Accessors
  // ----------------------------------------------------------------------- //

  def pitch: Direction = if (getWorld != null && getWorld.isBlockLoaded(getPos)) getBlockState.getBlock match {
    case rotatable if getWorld.getBlockState(getPos).hasProperty(PropertyRotatable.Pitch) => getWorld.getBlockState(getPos).get(PropertyRotatable.Pitch)
    case _ => Direction.NORTH
  } else null

  def pitch_=(value: Direction): Unit =
    trySetPitchYaw(value match {
      case Direction.DOWN | Direction.UP => value
      case _ => Direction.NORTH
    }, yaw)

  def yaw: Direction = if (getWorld != null && getWorld.isBlockLoaded(getPos)) getBlockState.getBlock match {
    case rotatable if getWorld.getBlockState(getPos).hasProperty(PropertyRotatable.Yaw) => getWorld.getBlockState(getPos).get(PropertyRotatable.Yaw)
    case rotatable if getWorld.getBlockState(getPos).hasProperty(PropertyRotatable.Facing) => getWorld.getBlockState(getPos).get(PropertyRotatable.Facing)
    case _ => Direction.SOUTH
  } else null

  def yaw_=(value: Direction): Unit =
    trySetPitchYaw(pitch, value match {
      case Direction.DOWN | Direction.UP => yaw
      case _ => value
    })

  def setFromEntityPitchAndYaw(entity: Entity) =
    trySetPitchYaw(
      pitch2Direction((entity.rotationPitch / 90).round + 1),
      yaw2Direction((entity.rotationYaw / 360 * 4).round & 3))

  def setFromFacing(value: Direction) =
    value match {
      case Direction.DOWN | Direction.UP =>
        trySetPitchYaw(value, yaw)
      case yaw =>
        trySetPitchYaw(Direction.NORTH, yaw)
    }

  def invertRotation() =
    trySetPitchYaw(pitch match {
      case Direction.DOWN | Direction.UP => pitch.getOpposite
      case _ => Direction.NORTH
    }, yaw.getOpposite)

  override def facing = pitch match {
    case Direction.DOWN | Direction.UP => pitch
    case _ => yaw
  }

  def rotate(axis: Direction) = {
    val block = getWorld.getBlock(position)
    val state = getWorld.getBlockState(position)
    if (state.getBlock != null) { // TODO The block returned from getBlock might always be !null...
      val valid = getValidRotations(state)
      if (valid != null && valid.contains(axis)) {
        val (newPitch, newYaw) = facing.getRotation(axis) match {
          case value@(Direction.UP | Direction.DOWN) =>
            if (value == pitch) (value, yaw.getRotation(axis))
            else (value, yaw)
          case value => (Direction.NORTH, value)
        }
        trySetPitchYaw(newPitch, newYaw)
      }
      else false
    }
    else false
  }

  /**
   * @TODO
   * This could possible be implemented better...
   */
  def getValidRotations(state: BlockState): Array[Direction] = {
    state.getProperties.asScala.foreach {
      case prop: DirectionProperty if prop.getName() == "facing" =>
        return prop.getAllowedValues.toArray.asInstanceOf[Array[Direction]]
    }
    null
  }

  override def toLocal(value: Direction) = if (value == null) null else {
    val p = pitch
    val y = yaw
    if (p != null && y != null) RotationHelper.toLocal(pitch, yaw, value) else null
  }

  override def toGlobal(value: Direction) = if (value == null) null else {
    val p = pitch
    val y = yaw
    if (p != null && y != null) RotationHelper.toGlobal(pitch, yaw, value) else null
  }

  def validFacings = Array(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)

  // ----------------------------------------------------------------------- //

  /**
   * TODO Packet stuff
   */
  protected def onRotationChanged() {
    if (isServer) {
//      ServerPacketSender.sendRotatableState(this)
    }
    else {
      getWorld.notifyBlockUpdate(getPos)
    }
    getWorld.notifyNeighborsOfStateChange(getPos, getBlockState.getBlock)
  }

  // ----------------------------------------------------------------------- //

  /** Updates cached translation array and sends notification to clients. */
  protected def updateTranslation(): Unit = {
    if (getWorld != null) {
      onRotationChanged()
    }
  }

  /** Validates new values against the allowed rotations as set in our block. */
  protected def trySetPitchYaw(pitch: Direction, yaw: Direction) = {
    val oldState = getWorld.getBlockState(getPos)
    def setState(newState: BlockState): Boolean = {
      if (oldState.hashCode() != newState.hashCode()) {
        getWorld.setBlockState(getPos, newState)
        updateTranslation()
        true
      }
      else false
    }
    getBlockState.getBlock match {
      case rotatable if oldState.hasProperty(PropertyRotatable.Pitch) && oldState.hasProperty(PropertyRotatable.Yaw) =>
        setState(oldState.`with`(PropertyRotatable.Pitch, pitch).`with`(PropertyRotatable.Yaw, yaw))
      case rotatable if oldState.hasProperty(PropertyRotatable.Facing) =>
        setState(oldState.`with`(PropertyRotatable.Facing, yaw))
      case _ => false
    }
  }
}
