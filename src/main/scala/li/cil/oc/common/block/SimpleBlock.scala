package li.cil.oc.common.block

import li.cil.oc.common.tileentity.traits.Colored
import net.minecraft.block.material.Material
import net.minecraft.block.{AbstractBlock, Block, BlockRenderType, BlockState, ContainerBlock, HorizontalBlock}
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.{DyeColor, DyeItem, ItemStack}
import net.minecraft.state.DirectionProperty
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.{BlockPos, BlockRayTraceResult}
import net.minecraft.util.{ActionResultType, Direction, Hand}
import net.minecraft.world.{IBlockReader, World}

import scala.jdk.CollectionConverters._

class SimpleBlock(material: Material = Material.IRON)
  extends ContainerBlock(AbstractBlock.Properties.create(material).hardnessAndResistance(2f, 5)) {

  override def getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

  def createItemStack(amount: Int = 1) = new ItemStack(this, amount)

  override def onBlockActivated(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType = {
    val heldItemStack = player.getHeldItem(hand)
    val heldItem = heldItemStack.getItem
    val te = world.getTileEntity(pos)
    te match {
      case colored: Colored if heldItem.getItem.isInstanceOf[DyeItem] =>
        colored.setColor(heldItem.getItem.asInstanceOf[DyeItem].getDyeColor.getColorValue)
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3)
        if (!player.isCreative && colored.consumesDye) {
          heldItemStack.shrink(1)
          ActionResultType.CONSUME
        }
        ActionResultType.PASS
      case _ => localOnBlockActivated(state, world, pos, player, hand, hit)
    }
  }

  def localOnBlockActivated(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType = ActionResultType.FAIL

  // Tile Entity ----------------------------------------------------------------------------------

  override def hasTileEntity(state: BlockState): Boolean = false

  override def createTileEntity(state: BlockState, world: IBlockReader): TileEntity = null
  // According to Forge, do not use this one!
  override def createNewTileEntity(worldIn: IBlockReader): TileEntity = null
}
