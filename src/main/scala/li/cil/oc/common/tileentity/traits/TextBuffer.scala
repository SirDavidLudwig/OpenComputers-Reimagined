package li.cil.oc.common.tileentity.traits

import li.cil.oc.{Constants, Settings, api}
import li.cil.oc.api.internal
import li.cil.oc.api.network.Node
import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}

/**
 * TODO Uncomment stuffs
 */
trait TextBuffer extends Environment with Tickable {
  lazy val buffer: internal.TextBuffer = {
//    val screenItem = api.Items.get(Constants.BlockName.ScreenTier1).createItemStack(1)
//    val buffer = api.Driver.driverFor(screenItem, getClass).createEnvironment(screenItem, this).asInstanceOf[api.internal.TextBuffer]
//    val (maxWidth, maxHeight) = Settings.screenResolutionsByTier(tier)
//    buffer.setMaximumResolution(maxWidth, maxHeight)
//    buffer.setMaximumColorDepth(Settings.screenDepthsByTier(tier))
//    buffer
    null
  }

  override def node: Node = null // buffer.node

  def tier: Int

  override def updateEntity() {
    super.updateEntity()
    if (isClient || isConnected) {
//      buffer.update()
    }
  }

  // ----------------------------------------------------------------------- //

  override def readForServer(state: BlockState, nbt: CompoundNBT): Unit = {
    super.readForServer(state, nbt)
//    buffer.load(nbt)
  }

  override def writeForServer(nbt: CompoundNBT): Unit = {
    super.writeForServer(nbt)
//    buffer.save(nbt)
  }

  @OnlyIn(Dist.CLIENT)
  override def readForClient(state: BlockState, nbt: CompoundNBT) {
    super.readForClient(state, nbt)
//    buffer.load(nbt)
  }

  override def writeForClient(nbt: CompoundNBT) {
    super.writeForClient(nbt)
//    buffer.save(nbt)
  }
}
