package li.cil.oc.common.block

import java.util

import li.cil.oc.common.tileentity
import li.cil.oc.common.tileentity.traits.{Colored, Rotatable}
import net.minecraft.block.material.Material
import net.minecraft.block.{AbstractBlock, Block, BlockRenderType, BlockState, ContainerBlock, HorizontalBlock}
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.{DyeColor, DyeItem, ItemStack, Rarity}
import net.minecraft.state.DirectionProperty
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.{BlockPos, BlockRayTraceResult}
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.{ActionResultType, Direction, Hand}
import net.minecraft.world.{IBlockReader, World}
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}

import scala.jdk.CollectionConverters._

class SimpleBlock(material: Material = Material.IRON)
  extends ContainerBlock(AbstractBlock.Properties.create(material).hardnessAndResistance(2f, 5)) {

  var showInItemList = true

  protected val validRotations_ = Array(Direction.UP, Direction.DOWN)

  def createItemStack(amount: Int = 1) = new ItemStack(this, amount)

  // ----------------------------------------------------------------------- //
  // Rendering
  // ----------------------------------------------------------------------- //

  override def getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

  // ----------------------------------------------------------------------- //
  // ItemBlock
  // ----------------------------------------------------------------------- //

  def rarity(stack: ItemStack) = Rarity.COMMON

  @OnlyIn(Dist.CLIENT)
  override def addInformation(stack: ItemStack, worldIn: IBlockReader, tooltip: util.List[ITextComponent], flagIn: ITooltipFlag): Unit = {
    tooltipHead(stack, worldIn, tooltip, flagIn)
    tooltipBody(stack, worldIn, tooltip, flagIn)
    tooltipTail(stack, worldIn, tooltip, flagIn)
  }

  protected def tooltipHead(stack: ItemStack, worldIn: IBlockReader, tooltip: util.List[ITextComponent], flagIn: ITooltipFlag): Unit = {
  }

  protected def tooltipBody(stack: ItemStack, worldIn: IBlockReader, tooltip: util.List[ITextComponent], flagIn: ITooltipFlag): Unit = {
  }

  protected def tooltipTail(stack: ItemStack, worldIn: IBlockReader, tooltip: util.List[ITextComponent], flagIn: ITooltipFlag): Unit = {
  }

  // ----------------------------------------------------------------------- //
  // Rotation
  // ----------------------------------------------------------------------- //

  def getFacing(world: IBlockReader, pos: BlockPos): Direction =
    world.getTileEntity(pos) match {
      case tileEntity: Rotatable => tileEntity.facing
      case _ => Direction.SOUTH
    }

  def setFacing(world: World, pos: BlockPos, value: Direction): Boolean =
    world.getTileEntity(pos) match {
      case rotatable: Rotatable => rotatable.setFromFacing(value); true
      case _ => false
    }

  def setRotationFromEntityPitchAndYaw(world: World, pos: BlockPos, value: Entity): Boolean =
    world.getTileEntity(pos) match {
      case rotatable: Rotatable => rotatable.setFromEntityPitchAndYaw(value); true
      case _ => false
    }

  def toLocal(world: IBlockReader, pos: BlockPos, value: Direction): Direction =
    world.getTileEntity(pos) match {
      case rotatable: Rotatable => rotatable.toLocal(value)
      case _ => value
    }

  // ----------------------------------------------------------------------- //
  // Block
  // ----------------------------------------------------------------------- //

  // ----------------------------------------------------------------------- //


  // ----------------------------------------------------------------------- //

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

  // Tile Entity ----------------------------------------------------------- //

  override def hasTileEntity(state: BlockState): Boolean = false

  override def createTileEntity(state: BlockState, world: IBlockReader): TileEntity = null

  /**
   * @WARN DO NOT USE
   * According to Forge, do not use this one!
   */
  @deprecated
  override def createNewTileEntity(worldIn: IBlockReader): TileEntity = null
}
