package tech.dlii.opencomputers.server.driver;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.api.driver.DriverAPI;
import tech.dlii.opencomputers.api.driver.item.DriverItem;

import java.util.ArrayList;

public class DriverRegistry implements DriverAPI {

    private boolean locked = false;
    private ArrayList<DriverItem> driverItems = new ArrayList<>();

    @Override
    public void add(DriverItem driver) {
        assertNotLocked();
    }

    @Override
    public @Nullable DriverItem driverFor(ItemStack stack) {
        return driverItems.stream().filter(item -> item.worksWith(stack)).findAny().orElse(null);
    }

    protected void assertNotLocked() {
        if (locked) {
            throw new IllegalStateException("All drivers must be registered in the 'init' phase.");
        }
    }
}
