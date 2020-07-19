package li.cil.oc.common.block
import java.util

import li.cil.oc.common.block.property.PropertyRotatable
import li.cil.oc.common.tileentity
import li.cil.oc.util.Rarity
import net.minecraft.block.{Block, BlockState}
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.item
import net.minecraft.item.ItemStack
import net.minecraft.state.StateContainer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.{IBlockReader, World}

class Screen(val tier: Int) extends RedstoneAware {

  /**
   * @TODO Look into PropertyTile from original code... this still needs it
   */
  override def fillStateContainer(builder: StateContainer.Builder[Block, BlockState]): Unit = {
    super.fillStateContainer(builder)
    builder.add(PropertyRotatable.Pitch)
    builder.add(PropertyRotatable.Yaw)
  }

  // ----------------------------------------------------------------------- //

  override def rarity(stack: ItemStack): item.Rarity = Rarity.byTier(tier)

  /**
   * @TODO tooltip
   */
  override protected def tooltipBody(stack: ItemStack, worldIn: IBlockReader, tooltip: util.List[ITextComponent], flagIn: ITooltipFlag): Unit = {
//    val (w, h) = Settings.screenResolutionsByTier(tier)
//    val depth = PackedColor.Depth.bits(Settings.screenDepthsByTier(tier))
//    tooltip.addAll(Tooltip.get(getClass.getSimpleName.toLowerCase, w, h, depth))
  }

  // ----------------------------------------------------------------------- //

  override def hasTileEntity(state: BlockState): Boolean = true

  override def createTileEntity(state: BlockState, world: IBlockReader): TileEntity = new tileentity.Screen(tier)

  // ----------------------------------------------------------------------- //

  override def onBlockPlacedBy(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity, stack: ItemStack): Unit = {
    super.onBlockPlacedBy(world, pos, state, placer, stack)
    world.getTileEntity(pos) match {
      case screen: tileentity.Screen => screen.delayUntilCheckForMultiBlock = 0
      case _ =>
    }
  }

  // ----------------------------------------------------------------------- //
}
