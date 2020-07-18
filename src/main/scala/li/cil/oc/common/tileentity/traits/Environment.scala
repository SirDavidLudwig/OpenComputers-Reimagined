package li.cil.oc.common.tileentity.traits

import li.cil.oc.Settings
import li.cil.oc.api.network
import li.cil.oc.api.network.{Connector, SidedEnvironment}
import li.cil.oc.util.ExtendedNBT._
import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction

trait Environment extends TileEntity with network.Environment with network.EnvironmentHost {
  protected var isChangeScheduled = false

  override def world = getWorld

  override def xPosition = x + 0.5

  override def yPosition = y + 0.5

  override def zPosition = z + 0.5

  override def markChanged() = if (this.isInstanceOf[Tickable]) isChangeScheduled = true else getWorld.markChunkDirty(getPos, this)

  protected def isConnected = node != null && node.address != null && node.network != null

  // ----------------------------------------------------------------------- //

  /**
   * @TODO EventHandler needs to be implemented
   */
  override protected def initialize() {
    super.initialize()
    if (isServer) {
//      EventHandler.scheduleServer(this)
    }
  }

  override def updateEntity() {
    super.updateEntity()
    if (isChangeScheduled) {
      getWorld.markChunkDirty(getPos, this)
      isChangeScheduled = false
    }
  }

  override def dispose() {
    super.dispose()
    if (isServer) {
      Option(node).foreach(_.remove)
      this match {
        case sidedEnvironment: SidedEnvironment => for (side <- Direction.values) {
          Option(sidedEnvironment.sidedNode(side)).foreach(_.remove())
        }
        case _ =>
      }
    }
  }

  // ----------------------------------------------------------------------- //

  private final val NodeTag = Settings.namespace + "node"

  override def readForServer(state: BlockState, nbt: CompoundNBT) {
    super.readForServer(state, nbt)
    if (node != null && node.host == this) {
      node.load(nbt.getCompound(NodeTag))
    }
  }

  override def writeForServer(nbt: CompoundNBT) {
    super.writeForServer(nbt)
    if (node != null && node.host == this) {
      nbt.putNewCompoundTag(NodeTag, node.save)
    }
  }

  // ----------------------------------------------------------------------- //

  override def onMessage(message: network.Message) {}

  override def onConnect(node: network.Node) {}

  override def onDisconnect(node: network.Node) {
    if (node == this.node) node match {
      case connector: Connector =>
        // Set it to zero to push all energy into other nodes, to
        // avoid energy loss when removing nodes. Set it back to the
        // original value though, as there are cases where the node
        // is re-used afterwards, without re-adjusting its buffer size.
        var bufferSize = connector.localBufferSize()
        connector.setLocalBufferSize(0)
        connector.setLocalBufferSize(bufferSize)
      case _ =>
    }
  }

  // ----------------------------------------------------------------------- //

  protected def result(args: Any*) = li.cil.oc.util.ResultWrapper.result(args: _*)
}
