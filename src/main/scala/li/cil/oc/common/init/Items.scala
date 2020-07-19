package li.cil.oc.common.init

import java.util.concurrent.Callable

import li.cil.oc.{Constants, OpenComputers}
import li.cil.oc.api.detail.{ItemAPI, ItemInfo}
import li.cil.oc.api.fs.FileSystem
import li.cil.oc.common.block
import li.cil.oc.common.block.SimpleBlock
import net.minecraft.block.Block
import net.minecraft.item.{DyeColor, Item, ItemStack}
import net.minecraftforge.event.RegistryEvent

import scala.collection.mutable

object Items extends ItemAPI {
  val descriptors = mutable.Map.empty[String, ItemInfo]

  val names = mutable.Map.empty[Any, String]

  val aliases = Map(
    "datacard" -> Constants.ItemName.DataCardTier1,
    "wlancard" -> Constants.ItemName.WirelessNetworkCardTier2
  )

  def registerBlock(instance: Block, id: String): Block = {
    if (!descriptors.contains(id)) {
      instance match {
        case simple: SimpleBlock =>
          instance.setRegistryName(id)
        case _ =>
      }
      descriptors += id -> new ItemInfo {
        override def name: String = id

        override def block = instance

        override def item = null

        override def createItemStack(size: Int): ItemStack = instance match {
          case simple: SimpleBlock => simple.createItemStack(size)
          case _ => new ItemStack(instance, size)
        }
      }
      names += instance -> id
    }
    instance
  }

  // Item Registration ----------------------------------------------------------------------------

  def registerBlockItems(event: RegistryEvent.Register[Item]): Unit = {
    descriptors.foreach {
      case (_, info) if info.block() != null =>
        val item: Item = new block.Item(info.block, new Item.Properties)
        item.setRegistryName(info.block.getRegistryName)
        event.getRegistry.register(item)
    }
  }

  def registerItems(event: RegistryEvent.Register[Item]): Unit = {

  }

  // Accessors ------------------------------------------------------------------------------------

  override def get(name: String): ItemInfo = descriptors.get(name).orNull

  override def get(stack: ItemStack): ItemInfo = null

  override def registerFloppy(name: String, color: DyeColor, factory: Callable[FileSystem], doRecipeCycling: Boolean): ItemStack = null

  override def registerEEPROM(name: String, code: Array[Byte], data: Array[Byte], readonly: Boolean): ItemStack = null
}
