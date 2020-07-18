package li.cil.oc.common.tileentity.traits

import li.cil.oc.common.inventory
import li.cil.oc.util.{BlockPosition, InventoryUtils}
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Direction

trait Inventory extends TileEntity with inventory.Inventory {
  private lazy val inventory = Array.fill[ItemStack](getSizeInventory)(ItemStack.EMPTY)

  def items = inventory

  // ----------------------------------------------------------------------- //

  override def isUsableByPlayer(player: PlayerEntity): Boolean =
    player.getDistanceSq(x + 0.5, y + 0.5, z + 0.5) <= 64

  // ----------------------------------------------------------------------- //

  def dropSlot(slot: Int, count: Int = getInventoryStackLimit, direction: Option[Direction] = None) =
    InventoryUtils.dropSlot(BlockPosition(x, y, z, getWorld), this, slot, count, direction)

  def dropAllSlots() =
    InventoryUtils.dropAllSlots(BlockPosition(x, y, z, getWorld), this)

  def spawnStackInWorld(stack: ItemStack, direction: Option[Direction] = None) =
    InventoryUtils.spawnStackInWorld(BlockPosition(x, y, z, getWorld), stack, direction)
}
