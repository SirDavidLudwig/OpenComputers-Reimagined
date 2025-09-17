package tech.dlii.opencomputers.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.common.config.Configuration;

public class HardDriveItem extends ComponentItem {
    public HardDriveItem(int tier, Properties properties) {
        super(SlotType.STORAGE, tier, properties);
    }

    public int size() {
        return Configuration.HDD_SIZES[tier()];
    }

    public int platterCount() {
        return Configuration.HDD_PLATTER_COUNTS[tier()];
    }

    @Override
    public Component getName(ItemStack itemStack) {
        String localizedName = super.getName(itemStack).getString();
        if (size() >= 1024) {
            localizedName += "(" + (size() / 1024) + " MB)";
        }
        else {
            localizedName += "(" + size() + " KB)";
        }
        return Component.literal(localizedName);
    }
}
