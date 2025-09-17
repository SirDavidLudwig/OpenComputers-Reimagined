package tech.dlii.opencomputers.api;

import net.minecraft.world.item.CreativeModeTab;
import tech.dlii.opencomputers.api.driver.DriverAPI;
import tech.dlii.opencomputers.api.machine.architecture.ArchitectureAPI;
import tech.dlii.opencomputers.api.machine.MachineAPI;
import tech.dlii.opencomputers.api.network.NetworkAPI;

import java.util.function.Supplier;

public class API {
    public static final String MOD_ID = "opencomputers";

    public static Supplier<CreativeModeTab> creativeTab;
    public static ArchitectureAPI architectures;
    public static DriverAPI driver;
    public static MachineAPI machine;
    public static NetworkAPI network;

    private API() {
    }
}
