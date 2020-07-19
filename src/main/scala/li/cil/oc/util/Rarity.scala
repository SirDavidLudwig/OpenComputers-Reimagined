package li.cil.oc.util

import net.minecraft.item

object Rarity {
  private val lookup = Array(item.Rarity.COMMON, item.Rarity.UNCOMMON, item.Rarity.RARE, item.Rarity.EPIC)

  def byTier(tier: Int) = lookup(tier max 0 min (lookup.length - 1))
}