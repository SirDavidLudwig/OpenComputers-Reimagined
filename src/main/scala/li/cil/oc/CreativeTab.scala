package li.cil.oc

import net.minecraft.item.{ItemGroup, ItemStack}

object CreativeTab extends ItemGroup(ItemGroup.getGroupCountSafe, OpenComputers.NAME) {
  private lazy val stack = api.Items.get(Constants.BlockName.CaseTier1).createItemStack(1)

  override def createIcon(): ItemStack = stack
}
