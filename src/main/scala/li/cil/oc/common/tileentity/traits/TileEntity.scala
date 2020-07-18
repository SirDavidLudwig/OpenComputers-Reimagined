package li.cil.oc.common.tileentity.traits

import li.cil.oc.client.Sound
import li.cil.oc.common.SaveHandler
import li.cil.oc.{OpenComputers, Settings, SideTracker}
import li.cil.oc.util.BlockPosition
import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}

/**
 * Because this requires a constructor parameter it can no longer be a trait
 */
trait TileEntity extends net.minecraft.tileentity.TileEntity {
  private final val IsServerDataTag = Settings.namespace + "isServerData"

  def x: Int = getPos.getX

  def y: Int = getPos.getY

  def z: Int = getPos.getZ

  def position = BlockPosition(x, y, z, getWorld)

  def isClient: Boolean = !isServer

  def isServer: Boolean = if (getWorld != null) !getWorld.isRemote else SideTracker.isServer

  // ----------------------------------------------------------------------- //

  /**
   * @TODO Setting here crashes
   */
  def updateEntity() {
//    val shouldUpdate = Settings.get.periodicallyForceLightUpdate
    val shouldUpdate = false

    if (shouldUpdate && getWorld.getGameTime % 40 == 0 && getBlockState.getBlock.getLightValue(getWorld.getBlockState(getPos), getWorld, getPos) > 0) {
      getWorld.notifyBlockUpdate(getPos, getWorld.getBlockState(getPos), getWorld.getBlockState(getPos), 3)
    }
  }

  override def validate() {
    super.validate()
    initialize()
  }

  override def remove() {
    super.remove()
    dispose()
  }

  override def onChunkUnloaded() {
    super.onChunkUnloaded()
    try dispose() catch {
      case t: Throwable => OpenComputers.log.error("Failed properly disposing a tile entity, things may leak and or break.", t)
    }
  }

  protected def initialize() {
  }

  def dispose() {
    if (isClient) {
      // Note: chunk unload is handled by sound via event handler.
      Sound.stopLoop(this)
    }
  }

  // ----------------------------------------------------------------------- //

  def readForServer(state: BlockState, nbt: CompoundNBT): Unit = super.func_230337_a_(state, nbt)

  def writeForServer(nbt: CompoundNBT): Unit = {
    nbt.putBoolean(IsServerDataTag, true)
    super.write(nbt)
  }

  @OnlyIn(Dist.CLIENT)
  def readForClient(state: BlockState, nbt: CompoundNBT) {}

  def writeForClient(nbt: CompoundNBT): Unit = {
    nbt.putBoolean(IsServerDataTag, false)
  }

  // ----------------------------------------------------------------------- //

  override def func_230337_a_(state: BlockState, nbt: CompoundNBT): Unit = {
    if (isServer || nbt.getBoolean(IsServerDataTag)) {
      readForServer(state, nbt)
    }
    else {
      readForClient(state, nbt)
    }
  }

  override def write(nbt: CompoundNBT): CompoundNBT = {
    if (isServer) {
      writeForServer(nbt)
    }
    nbt
  }

  override def getUpdatePacket: SUpdateTileEntityPacket = {
    // Obfuscation workaround. If it works.
    val te = this.asInstanceOf[net.minecraft.tileentity.TileEntity]
    /** WARN tileEntityTypeIn is new... Not sure what to do with this */
    new SUpdateTileEntityPacket(te.getPos, 0, te.getUpdateTag)
  }

  override def getUpdateTag: CompoundNBT = {
    val nbt = super.getUpdateTag

    // See comment on savingForClients variable.
//    SaveHandler.savingForClients = true
//    try {
//      try writeForClient(nbt) catch {
//        case e: Throwable => OpenComputers.log.warn("There was a problem writing a TileEntity description packet. Please report this if you see it!", e)
//      }
//    } finally {
//      SaveHandler.savingForClients = false
//    }

    nbt
  }

  override def onDataPacket(manager: NetworkManager, packet: SUpdateTileEntityPacket): Unit = {
    try deserializeNBT(packet.getNbtCompound) catch {
      case e: Throwable => OpenComputers.log.warn("There was a problem reading a TileEntity description packet. Please report this if you see it!", e)
    }
  }
}
