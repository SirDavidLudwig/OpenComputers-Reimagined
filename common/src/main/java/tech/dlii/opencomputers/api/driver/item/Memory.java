package tech.dlii.opencomputers.api.driver.item;

import net.minecraft.world.item.ItemStack;

public interface Memory extends DriverItem {
    double amount(ItemStack stack);
}
