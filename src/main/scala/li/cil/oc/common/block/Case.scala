package li.cil.oc.common.block

import li.cil.oc.common.tileentity
import net.minecraft.block.material.Material
import net.minecraft.block.{AbstractBlock, Block, BlockState, HorizontalBlock}
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.{BooleanProperty, StateContainer}
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tileentity.{TileEntity, TileEntityType}
import net.minecraft.util.Direction
import net.minecraft.world.IBlockReader

class Case(val tier: Int) extends RedstoneAware {

  def this() = {
    this(0)
    setDefaultState(getDefaultState.`with`(HorizontalBlock.HORIZONTAL_FACING, Direction.NORTH).`with`(li.cil.oc.common.init.Properties.RUNNING, java.lang.Boolean.valueOf(false)))
  }

  /**
   * Set the properties when the player places the block
   * @return
   */
  override def getStateForPlacement(context: BlockItemUseContext): BlockState = {
    this.getDefaultState.`with`(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing.getOpposite)
  }

  /**
   * Create the list of properties the block can have
   */
  override def fillStateContainer(builder: StateContainer.Builder[Block, BlockState]): Unit = {
    super.fillStateContainer(builder)
    builder.add(HorizontalBlock.HORIZONTAL_FACING)
    builder.add(li.cil.oc.common.init.Properties.RUNNING)
  }

//  override def hasTileEntity(state: BlockState): Boolean = true
//
//  override def createTileEntity(state: BlockState, world: IBlockReader): TileEntity = new tileentity.Case()
}