package tech.dlii.opencomputers.server.driver;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.driver.DriverAPI;
import tech.dlii.opencomputers.api.driver.item.DriverItem;

import java.util.ArrayList;

public class Drivers implements DriverAPI {

    private boolean locked = false;
    private ArrayList<DriverItem> driverItems = new ArrayList<>();

    public static void initialize() {
        API.driver = new Drivers();
        API.driver.register(new CPUDriver());
        API.driver.register(new GPUDriver());
        API.driver.register(new MemoryDriver());
        API.driver.register(new StorageDriver());
    }

    @Override
    public void register(DriverItem driver) {
        assertNotLocked();
        driverItems.add(driver);
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
