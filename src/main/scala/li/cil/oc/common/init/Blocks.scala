package li.cil.oc.common.init

import java.util.function.Supplier

import li.cil.oc.api.detail.ItemInfo
import li.cil.oc.common.block.SimpleBlock
import li.cil.oc.{Constants, OpenComputers, Settings}
import li.cil.oc.common.{Tier, block, tileentity}
import net.minecraft.block.Block
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.tileentity.{TileEntity, TileEntityType}
import net.minecraftforge.event.RegistryEvent

import scala.collection.mutable

object Blocks {

  // Block/tile entity registration ---------------------------------------------------------------

//  val TILE_ENTITY_CASE_1 = registerTileEntity(new block.Case(Tier.One), () => new tileentity.Case, Constants.BlockName.CaseTier1)

  val CASE_1 = registerBlock(new block.Case(Tier.One), "case1")

  val TILE_ENTITY_CASE = TileEntityType.Builder.create(() => new tileentity.Case, CASE_1).build(null).setRegistryName("case")


  // Tile entity type registration ----------------------------------------------------------------

//  def registerTileEntity[T <: TileEntity](instance: Block, factory: Supplier[T], id: String): TileEntityType[T] = {
//    registerBlock(instance, id)
//    val tileEntityType: TileEntityType[T] = TileEntityType.Builder.create(factory, instance).build(null)
//    tileEntityType.setRegistryName(id)
//    tileEntityType
//  }

  def registerBlock(instance: Block, id: String): Block = {
    Items.registerBlock(instance, id)
    instance
  }

  def registerBlocks(event: RegistryEvent.Register[Block]): Unit = {
//    instances.foreach { event.getRegistry.register }
    Items.descriptors.foreach { case (_,info) => event.getRegistry.register(info.block)}
  }

  def registerTileEntities(event: RegistryEvent.Register[TileEntityType[_]]): Unit = {
    event.getRegistry.register(TILE_ENTITY_CASE)
  }
}
