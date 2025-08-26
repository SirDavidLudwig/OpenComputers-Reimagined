package tech.dlii.opencomputers.api;

import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public class API {
    public static final String MOD_ID = "opencomputers";

    public static Supplier<CreativeModeTab> creativeTab;

    private API() {
    }
}
