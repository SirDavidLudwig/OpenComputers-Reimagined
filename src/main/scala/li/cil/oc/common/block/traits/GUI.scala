package li.cil.oc.common.block.traits

import li.cil.oc.common.block.SimpleBlock
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.{BlockPos, BlockRayTraceResult}
import net.minecraft.util.{ActionResultType, Hand}
import net.minecraft.world.World

class GUI extends SimpleBlock {
//  def guiType: GuiType.EnumVal
  override def localOnBlockActivated(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType = super.localOnBlockActivated(state, world, pos, player, hand, hit)
}
