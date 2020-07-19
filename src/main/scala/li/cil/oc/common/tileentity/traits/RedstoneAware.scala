package li.cil.oc.common.tileentity.traits

import java.util

import li.cil.oc.Settings
import li.cil.oc.integration.util.BundledRedstone
import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}

case class RedstoneChangedEventArgs (side: Direction, oldValue: Int, newValue: Int, color: Int = -1)

trait RedstoneAware extends RotationAware {
  protected[tileentity] val _input: Array[Int] = Array.fill(6)(-1)

  protected[tileentity] val _output: Array[Int] = Array.fill(6)(0)

  protected var _isOutputEnabled: Boolean = false

  protected var shouldUpdateInput = true

  def isOutputEnabled: Boolean = _isOutputEnabled

  def setOutputEnabled(value: Boolean): RedstoneAware = {
    if (value != _isOutputEnabled) {
      _isOutputEnabled = value
      if (!value) {
        for (i <- _output.indices) {
          _output(i) = 0
        }
      }
      onRedstoneOutputEnabledChanged()
    }
    this
  }

  protected def getObjectFuzzy(map: util.Map[_, _], key: Int): Option[AnyRef] = {
    val refMap: util.Map[AnyRef, AnyRef] = map.asInstanceOf[util.Map[AnyRef, AnyRef]]
    if (refMap.containsKey(key))
      Option(refMap.get(key))
    else if (refMap.containsKey(new Integer(key)))
      Option(refMap.get(new Integer(key)))
    else if (refMap.containsKey(new Integer(key) * 1.0))
      Option(refMap.get(new Integer(key) * 1.0))
    else if (refMap.containsKey(key * 1.0))
      Option(refMap.get(key * 1.0))
    else
      None
  }

  protected def valueToInt(value: AnyRef): Option[Int] = {
    value match {
      case Some(num: Number) => Option(num.intValue)
      case _ => None
    }
  }

  def getInput: Array[Int] = _input.map(math.max(_, 0))

  def getInput(side: Direction): Int = _input(side.ordinal) max 0

  def setInput(side: Direction, newInput: Int): Unit = {
    val oldInput = _input(side.ordinal())
    _input(side.ordinal()) = newInput
    if (oldInput >= 0 && newInput != oldInput) {
      onRedstoneInputChanged(RedstoneChangedEventArgs(side, oldInput, newInput))
    }
  }

  def setInput(values: Array[Int]): Unit = {
    for (side <- Direction.values) {
      val value = if (side.ordinal <= values.length) values(side.ordinal) else 0
      setInput(side, value)
    }
  }

  def maxInput: Int = _input.map(math.max(_, 0)).max

  def getOutput: Array[Int] = Direction.values.map{ side: Direction => _output(toLocal(side).ordinal) }

  def getOutput(side: Direction) = _output(toLocal(side).ordinal())

  def setOutput(side: Direction, value: Int): Boolean = {
    if (value == getOutput(side)) return false
    _output(toLocal(side).ordinal()) = value
    onRedstoneOutputChanged(side)
    true
  }

  def setOutput(values: util.Map[_, _]): Boolean = {
    var changed: Boolean = false
    Direction.values.foreach(side => {
      val sideIndex = toLocal(side).ordinal
      // due to a bug in our jnlua layer, I cannot loop the map
      valueToInt(getObjectFuzzy(values, sideIndex)) match {
        case Some(num: Int) if setOutput(side, num) => changed = true
        case _ =>
      }
    })
    changed
  }

  def checkRedstoneInputChanged() {
    if (this.isInstanceOf[Tickable]) {
      shouldUpdateInput = isServer
    } else {
      Direction.values().foreach(updateRedstoneInput)
    }
  }

  // ----------------------------------------------------------------------- //

  override def updateEntity() {
    super.updateEntity()
    if (isServer) {
      if (shouldUpdateInput) {
        shouldUpdateInput = false
        Direction.values().foreach(updateRedstoneInput)
      }
    }
  }

  /**
   * @TODO EventHandler
   */
  override def validate(): Unit = {
    super.validate()
    if (!this.isInstanceOf[Tickable]) {
//      EventHandler.scheduleServer(() => Direction.values().foreach(updateRedstoneInput))
    }
  }

  def updateRedstoneInput(side: Direction): Unit = setInput(side, BundledRedstone.computeInput(position, side))

  // ----------------------------------------------------------------------- //

  override def readForServer(state: BlockState, nbt: CompoundNBT): Unit = {
    super.readForServer(state, nbt)

    val input = nbt.getIntArray(Settings.namespace + "rs.input")
    input.copyToArray(_input, 0, input.length min _input.length)
    val output = nbt.getIntArray(Settings.namespace + "rs.output")
    output.copyToArray(_output, 0, output.length min _output.length)
  }

  override def writeForServer(nbt: CompoundNBT): Unit = {
    super.writeForServer(nbt)

    nbt.putIntArray(Settings.namespace + "rs.input", _input)
    nbt.putIntArray(Settings.namespace + "rs.output", _output)
  }

  @OnlyIn(Dist.CLIENT)
  override def readForClient(state: BlockState, nbt: CompoundNBT) {
    super.readForClient(state, nbt)
    _isOutputEnabled = nbt.getBoolean("isOutputEnabled")
    nbt.getIntArray("output").copyToArray(_output)
  }

  override def writeForClient(nbt: CompoundNBT) {
    super.writeForClient(nbt)
    nbt.putBoolean("isOutputEnabled", _isOutputEnabled)
    nbt.putIntArray("output", _output)
  }

  // ----------------------------------------------------------------------- //

  protected def onRedstoneInputChanged(args: RedstoneChangedEventArgs) {}

  /**
   * @TODO ServerPacketSender
   * The two functions below
   */
  protected def onRedstoneOutputEnabledChanged() {
//    if (getWorld != null) {
//      getWorld.notifyNeighborsOfStateChange(getPos, getBlockState.getBlock)
//      if (isServer) ServerPacketSender.sendRedstoneState(this)
//      else getWorld.notifyBlockUpdate(getPos, getWorld.getBlockState(getPos), getWorld.getBlockState(getPos), 3)
//    }
  }

  protected def onRedstoneOutputChanged(side: Direction) {
//    val blockPos = getPos.offset(side)
//    getWorld.neighborChanged(blockPos, getBlockState.getBlock, blockPos)
//    getWorld.notifyNeighborsOfStateExcept(blockPos, getWorld.getBlockState(blockPos).getBlock, side.getOpposite)
//
//    if (isServer) ServerPacketSender.sendRedstoneState(this)
//    else getWorld.notifyBlockUpdate(getPos, getWorld.getBlockState(getPos), getWorld.getBlockState(getPos), 3)
  }
}
