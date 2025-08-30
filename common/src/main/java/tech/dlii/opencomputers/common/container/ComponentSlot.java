package tech.dlii.opencomputers.common.container;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.Slot;
import tech.dlii.opencomputers.client.Textures;

public class ComponentSlot extends net.minecraft.world.inventory.Slot {

    public final String slot;
    public final int tier;

    public final ResourceLocation tierIcon;
    public final ResourceLocation background;

    public ComponentSlot(Container container, int index, int x, int y, String slot, int tier) {
        super(container, index, x, y);
        this.slot = slot;
        this.tier = tier;

        this.tierIcon = switch (tier) {
            case Tier.ONE -> Textures.Icon.TIER_ONE;
            case Tier.TWO -> Textures.Icon.TIER_TWO;
            case Tier.THREE -> Textures.Icon.TIER_THREE;
            default -> null;
        };

        this.background = switch (slot) {
            case Slot.CPU -> Textures.Icon.CPU;
            default -> null;
        };
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        if (!container.canPlaceItem(getContainerSlot(), itemStack)) {
            return false;
        }
        if (slot.equals(Slot.NONE) || tier == Tier.NONE) {
            return false;
        }
        if (slot == Slot.ANY && tier == Tier.ANY) {
            return true;
        }
        if (slot == Slot.TOOL) {
            return true;
        }
        // Needs finishing
        return true;
    }
}
