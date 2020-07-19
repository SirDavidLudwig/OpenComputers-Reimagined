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
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries}

import scala.collection.mutable

object Blocks {

  // Block/tile entity registration ---------------------------------------------------------------

  val CASE_1 = registerBlock("case1", new block.Case(Tier.One))
  val SCREEN_1 = registerBlock("screen1", new block.Screen(Tier.One))

//  val CASE_1 = BLOCKS.register("case1", () => new block.Case(Tier.One))
//  val SCREEN_1 = BLOCKS.register("screen1", () => new block.Screen(Tier.One))

  // ----------------------------------------------------------------------- //

  val TILE_ENTITY_CASE = TileEntityType.Builder.create(() => new tileentity.Case, CASE_1).build(null).setRegistryName("case")
  val TILE_ENTITY_SCREEN =  TileEntityType.Builder.create(() => new tileentity.Screen, SCREEN_1).build(null).setRegistryName("screen")


  // Tile entity type registration ----------------------------------------------------------------

  def registerBlock(id: String, block: Block) = {
    Items.registerBlock(block, id)
    block
  }

  def registerBlocks(event: RegistryEvent.Register[Block]): Unit = {
//    instances.foreach { event.getRegistry.register }
//    Items.descriptors.foreach { case (_,info) => event.getRegistry.register(info.block)}
    event.getRegistry.registerAll(
      CASE_1,
      SCREEN_1
    )
  }

  def registerTileEntities(event: RegistryEvent.Register[TileEntityType[_]]): Unit = {
    event.getRegistry.registerAll(
      TILE_ENTITY_CASE,
      TILE_ENTITY_SCREEN
    )
  }
}
