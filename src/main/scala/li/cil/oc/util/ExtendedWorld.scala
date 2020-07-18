package li.cil.oc.util

import li.cil.oc.api.network.EnvironmentHost
import net.minecraft.block.{Block, BlockState}
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.{IBlockReader, World}

object ExtendedWorld {

  implicit def extendedBlockAccess(world: IBlockReader): ExtendedBlockAccess = new ExtendedBlockAccess(world)

  implicit def extendedWorld(world: World): ExtendedWorld = new ExtendedWorld(world)

  class ExtendedBlockAccess(val world: IBlockReader) {
    def getBlock(position: BlockPosition) = getBlockState(position).getBlock

    def getBlockState(position: BlockPosition) = world.getBlockState(position.toBlockPos)

    def getBlockMapColor(position: BlockPosition) = getBlockMetadata(position).getMaterialColor(world, position.toBlockPos)

    def getBlockMetadata(position: BlockPosition) = world.getBlockState(position.toBlockPos)

    def getTileEntity(position: BlockPosition): TileEntity = world.getTileEntity(position.toBlockPos)

    def getTileEntity(host: EnvironmentHost): TileEntity = getTileEntity(BlockPosition(host))

//    def isAirBlock(position: BlockPosition) = world.isAirBlock(position.toBlockPos)

    def getLightBrightnessForSkyBlocks(position: BlockPosition) = world.getLightValue(position.toBlockPos)
  }

  class ExtendedWorld(override val world: World) extends ExtendedBlockAccess(world) {
    def blockExists(position: BlockPosition) = world.isBlockLoaded(position.toBlockPos)

    def breakBlock(position: BlockPosition, drops: Boolean = true) = world.destroyBlock(position.toBlockPos, drops)

    def destroyBlockInWorldPartially(entityId: Int, position: BlockPosition, progress: Int) = world.sendBlockBreakProgress(entityId, position.toBlockPos, progress)

//    def extinguishFire(player: PlayerEntity, position: BlockPosition, side: Direction) = world.extinguishFire(player, position.toBlockPos, side)

    def getBlockHardness(position: BlockPosition) = getBlockState(position).getBlockHardness(world, position.toBlockPos)

    def getBlockHarvestLevel(position: BlockPosition) = getBlockState(position).getHarvestLevel

    def getBlockHarvestTool(position: BlockPosition) = getBlockState(position).getHarvestTool

    def computeRedstoneSignal(position: BlockPosition, side: Direction) = math.max(world.isBlockProvidingPowerTo(position.offset(side), side), world.getIndirectPowerLevelTo(position.offset(side), side))

    def isBlockProvidingPowerTo(position: BlockPosition, side: Direction) = world.getStrongPower(position.toBlockPos, side)

    def getIndirectPowerLevelTo(position: BlockPosition, side: Direction) = world.getRedstonePower(position.toBlockPos, side)

    def notifyBlockUpdate(pos: BlockPos): Unit = world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3)

    def notifyBlockUpdate(position: BlockPosition): Unit = notifyBlockUpdate(position, world.getBlockState(position.toBlockPos), world.getBlockState(position.toBlockPos))

    def notifyBlockUpdate(position: BlockPosition, oldState: BlockState, newState: BlockState, flags: Int = 3): Unit = world.notifyBlockUpdate(position.toBlockPos, oldState, newState, flags)

    def notifyBlockOfNeighborChange(position: BlockPosition, block: Block) = world.neighborChanged(position.toBlockPos, block, position.toBlockPos)

    def notifyBlocksOfNeighborChange(position: BlockPosition, block: Block) = world.notifyNeighborsOfStateChange(position.toBlockPos, block)

    def notifyBlocksOfNeighborChange(position: BlockPosition, block: Block, side: Direction) = world.notifyNeighborsOfStateExcept(position.toBlockPos, block, side)

    def playAuxSFX(id: Int, position: BlockPosition, data: Int) = world.playEvent(id, position.toBlockPos, data)

    def setBlock(position: BlockPosition, block: Block) = world.setBlockState(position.toBlockPos, block.getDefaultState)

//    def setBlock(position: BlockPosition, blockState) = // world.setBlockState(position.toBlockPos, block.getStateFromMeta(metadata), flag)

//    def setBlockToAir(position: BlockPosition) = world.setBlockToAir(position.toBlockPos)

    def isSideSolid(position: BlockPosition, entity: Entity, side: Direction) = world.func_234929_a_(position.toBlockPos, entity, side)

    def isBlockLoaded(position: BlockPosition) = world.isBlockLoaded(position.toBlockPos)
  }

}
