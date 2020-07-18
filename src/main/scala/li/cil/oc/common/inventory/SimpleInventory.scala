package li.cil.oc.common.inventory

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

/**
 * @WARN
 * May need to extend with `INameable` to get some extra properties back that are missing...
 */
trait SimpleInventory extends IInventory {

  def getInventoryStackRequired = 1

  override def decrStackSize(slot: Int, amount: Int): ItemStack = {
    if (slot >= 0 && slot < getSizeInventory) {
      (getStackInSlot(slot) match {
        case stack: ItemStack if stack.getCount - amount < getInventoryStackRequired =>
          setInventorySlotContents(slot, ItemStack.EMPTY)
          stack
        case stack: ItemStack =>
          val result = stack.shrink(amount)
          markDirty()
          result
        case _ => ItemStack.EMPTY
      }) match {
        case stack: ItemStack if stack.getCount > 0 => stack
        case _ => ItemStack.EMPTY
      }
    }
    else ItemStack.EMPTY
  }

  override def removeStackFromSlot(slot: Int): ItemStack = {
    if (slot >= 0 && slot < getSizeInventory) {
      val stack = getStackInSlot(slot)
      setInventorySlotContents(slot, ItemStack.EMPTY)
      stack
    }
    else ItemStack.EMPTY
  }

  override def clear(): Unit = {
    for (slot <- 0 until getSizeInventory) {
      setInventorySlotContents(slot, ItemStack.EMPTY)
    }
  }
}
