package li.cil.oc.common.block

import net.minecraft.block.material.Material
import net.minecraft.block.{AbstractBlock, Block, BlockRenderType, BlockState, ContainerBlock}
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.{IBlockReader, World}

class SimpleBlock(material: Material = Material.IRON)
  extends ContainerBlock(AbstractBlock.Properties.create(material).hardnessAndResistance(2f, 5)) {


  override def getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

  def createItemStack(amount: Int = 1) = new ItemStack(this, amount)

  override def hasTileEntity(state: BlockState): Boolean = false
//
  override def createTileEntity(state: BlockState, world: IBlockReader): TileEntity = null
//
//  // According to Forge, do not use this one!
  override def createNewTileEntity(worldIn: IBlockReader): TileEntity = null
}
