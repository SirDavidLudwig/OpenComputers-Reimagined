package li.cil.oc.common.init

import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.tileentity.{TileEntity, TileEntityType}
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

object RegistrationHandler {

  @SubscribeEvent
  def registerBlocks(event: RegistryEvent.Register[Block]): Unit = {
    Blocks.registerBlocks(event)
  }

  @SubscribeEvent
  def registerModels(event: RegistryEvent.Register[TileEntityType[_]]): Unit = {
    Blocks.registerTileEntities(event)
  }

  @SubscribeEvent
  def registerItems(event: RegistryEvent.Register[Item]): Unit = {
    Items.registerBlockItems(event)
    Items.registerItems(event)
  }
}
