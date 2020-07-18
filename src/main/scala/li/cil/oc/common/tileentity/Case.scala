package li.cil.oc.common.tileentity

import java.util

import li.cil.oc.api.Driver
import li.cil.oc.{Constants, Settings, common}
import li.cil.oc.api.driver.DeviceInfo
import li.cil.oc.api.driver.DeviceInfo.{DeviceAttribute, DeviceClass}
import li.cil.oc.api.network.Connector
import li.cil.oc.common.block.property.PropertyRunning
import li.cil.oc.common.Tier
import li.cil.oc.common.init.Blocks
import li.cil.oc.util.Color
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.extensions.IForgeTileEntity

import scala.jdk.CollectionConverters._

class Case(var tier: Int) extends TileEntity(Blocks.TILE_ENTITY_CASE) with traits.TileEntity with traits.Computer with traits.Colored with DeviceInfo with IForgeTileEntity {

  def this() = {
    this(0)
    // If no tier was defined when constructing this case, then we don't yet know the inventory size
    // this is set back to true when the nbt data is loaded
    isSizeInventoryReady = false

    this.hashCode
  }

  // Used on client side to check whether to render disk activity/network indicators.
  var lastFileSystemAccess = 0L
  var lastNetworkActivity = 0L

  setColor(Color.byTier(tier).getColorValue)

  private final lazy val deviceInfo = Map(
    DeviceAttribute.Class -> DeviceClass.System,
    DeviceAttribute.Description -> "Computer",
    DeviceAttribute.Vendor -> Constants.DeviceInfo.DefaultVendor,
    DeviceAttribute.Product -> "Blocker",
    DeviceAttribute.Capacity -> getSizeInventory.toString
  )



  override def getDeviceInfo: util.Map[String, String] = deviceInfo.asJava

  // ----------------------------------------------------------------------- //

  def isCreative = tier == Tier.Four

  // ----------------------------------------------------------------------- //

  override def componentSlot(address: String) = components.indexWhere(_.exists(env => env.node != null && env.node.address == address))

  // ----------------------------------------------------------------------- //

  override def updateEntity() {
    if (isServer && isCreative && getWorld.getGameTime % Settings.get.tickFrequency == 0) {
      // Creative case, make it generate power.
      node.asInstanceOf[Connector].changeBuffer(Double.PositiveInfinity)
    }
    super.updateEntity()
  }

  // ----------------------------------------------------------------------- //

  override protected def onRunningChanged(): Unit = {
    super.onRunningChanged()
    getBlockState.getBlock match {
      case block: common.block.Case => {
        val state = getWorld.getBlockState(getPos)
        // race condition that the world no longer has this block at the position (e.g. it was broken)
        if (block == state.getBlock) {
          getWorld.setBlockState(getPos, state.`with`(PropertyRunning.Running, Boolean.box(isRunning)))
        }
      }
      case _ =>
    }
  }

  // ----------------------------------------------------------------------- //

  private final val TierTag = Settings.namespace + "tier"

  override def readForServer(state: BlockState, nbt: CompoundNBT): Unit = {
    tier = nbt.getByte(TierTag) max 0 min 3
    setColor(Color.byTier(tier).getColorValue)
    super.readForServer(state, nbt)
    isSizeInventoryReady = true
  }

  override def writeForServer(nbt: CompoundNBT): Unit = {
    nbt.putByte(TierTag, tier.toByte)
    super.writeForServer(nbt)
  }

  // ----------------------------------------------------------------------- //

  override protected def onItemAdded(slot: Int, stack: ItemStack) {
    super.onItemAdded(slot, stack)
    if (isServer) {
//      if (InventorySlots.computer(tier)(slot).slot == Slot.Floppy) {
//        common.Sound.playDiskInsert(this)
//      }
    }
  }

  override protected def onItemRemoved(slot: Int, stack: ItemStack) {
    super.onItemRemoved(slot, stack)
    if (isServer) {
//      val slotType = InventorySlots.computer(tier)(slot).slot
//      if (slotType == Slot.Floppy) {
//        common.Sound.playDiskEject(this)
//      }
//      if (slotType == Slot.CPU) {
//        machine.stop()
//      }
    }
  }

  /**
   * @TODO This needs to be implemented after InventorySlots
   */
  override def getSizeInventory: Int = 0

  override def isUsableByPlayer(player: PlayerEntity): Boolean =
    super.isUsableByPlayer(player) && (!isCreative || player.isCreative)

//  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean =
//    Option(Driver.driverFor(stack, getClass)).fold(false)(driver => {
//      val provided = InventorySlots.computer(tier)(slot)
//      driver.slot(stack) == provided.slot && driver.tiar(stack) <= provided.tier
//    })
}