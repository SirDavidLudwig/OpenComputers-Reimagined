package tech.dlii.opencomputers.common.inventory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.client.Textures;

public class ComponentSlot extends net.minecraft.world.inventory.Slot {

    public final String slot;
    public final int tier;

    public final @Nullable ResourceLocation tierIcon;
    public final @Nullable ResourceLocation background;

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
            case SlotType.CARD -> Textures.Icon.CARD;
            case SlotType.COMPONENT_BUS -> Textures.Icon.COMPONENT_BUS;
            case SlotType.CONTAINER -> Textures.Icon.CONTAINER;
            case SlotType.CPU -> Textures.Icon.CPU;
            case SlotType.EEPROM -> Textures.Icon.EEPROM;
            case SlotType.FLOPPY -> Textures.Icon.FLOPPY;
            case SlotType.HDD -> Textures.Icon.HDD;
            case SlotType.MEMORY -> Textures.Icon.MEMORY;
            default -> null;
        };
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        if (!container.canPlaceItem(getContainerSlot(), itemStack)) {
            return false;
        }
        if (slot.equals(SlotType.NONE) || tier == Tier.NONE) {
            return false;
        }
        if (slot == SlotType.ANY && tier == Tier.ANY) {
            return true;
        }
        if (slot == SlotType.TOOL) {
            return true;
        }
//        if (slot == itemStack.getItem().slotType)
        // Needs finishing
        return true;
    }
}
