package li.cil.oc.common.block

import li.cil.oc.common.block.property.{PropertyRotatable, PropertyRunning}
import li.cil.oc.common.tileentity
import net.minecraft.block.{Block, BlockState, HorizontalBlock}
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.{BlockItemUseContext}
import net.minecraft.state.StateContainer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.{BlockPos, BlockRayTraceResult}
import net.minecraft.util.{ActionResultType, Direction, Hand}
import net.minecraft.world.{IBlockReader, World}

class Case(val tier: Int) extends RedstoneAware {

  setDefaultState(getDefaultState.`with`(PropertyRotatable.Facing, Direction.NORTH).`with`(PropertyRunning.Running, java.lang.Boolean.valueOf(false)))


  override def localOnBlockActivated(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType = {
    if (player.isSneaking) {
      if (world.isRemote) world.getTileEntity(pos) match {
        case computer: tileentity.Case => computer.hasErrored = !computer.hasErrored
        case _ =>
      }
    } else {
      if (player.getHeldItem(hand).getItem.getRegistryName().getPath == "stick") {
        world.getTileEntity(pos) match {
          case computer: tileentity.Case => computer.setRunning(!computer.isRunning)
          case _ =>
        }
      } else {
        world.getTileEntity(pos) match {
          case computer: tileentity.Case => computer.lastFileSystemAccess = System.currentTimeMillis()
          case _ =>
        }
      }
    }
    ActionResultType.SUCCESS
//    else super.localOnBlockActivated(state, world, pos, player, hand, hit)
  }

  /**
   * Set the properties when the player places the block
   *
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
    builder.add(PropertyRotatable.Facing)
    builder.add(PropertyRunning.Running)
  }

  override def hasTileEntity(state: BlockState): Boolean = true

  override def createTileEntity(state: BlockState, world: IBlockReader): TileEntity = new tileentity.Case(tier)
}