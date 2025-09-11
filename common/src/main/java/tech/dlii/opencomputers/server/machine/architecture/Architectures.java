package tech.dlii.opencomputers.server.machine.architecture;

import net.minecraft.resources.ResourceLocation;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.machine.Architecture;
import tech.dlii.opencomputers.api.machine.ArchitectureAPI;
import tech.dlii.opencomputers.server.machine.architecture.luac.NativeLua52Architecture;
import tech.dlii.opencomputers.server.machine.architecture.luac.NativeLua53Architecture;
import tech.dlii.opencomputers.server.machine.architecture.luac.NativeLua54Architecture;
import tech.dlii.opencomputers.server.machine.architecture.luaj.LuaJLuaArchitecture;

import java.util.LinkedHashMap;
import java.util.Map;

public class Architectures implements ArchitectureAPI {

    private final LinkedHashMap<ResourceLocation, Class<? extends Architecture>> architectures = new LinkedHashMap<>();

    public static void initialize() {
        API.architectures = new Architectures();
        register("luaj", LuaJLuaArchitecture.class);
//        register("lua52", NativeLua52Architecture.class);
//        register("lua53", NativeLua53Architecture.class);
//        register("lua54", NativeLua54Architecture.class);
    }

    private static void register(String name, Class<? extends Architecture> architecture) {
        API.architectures.register(ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name), architecture);
    }

    @Override
    public void register(ResourceLocation identifier, Class<? extends Architecture> architecture) {
        architectures.put(identifier, architecture);
    }

    @Override
    public Map<ResourceLocation, Class<? extends Architecture>> architectures() {
        return this.architectures;
    }

    @Override
    public Map.Entry<ResourceLocation, Class<? extends Architecture>> defaultArchitecture() {
        return architectures.firstEntry();
    }

    @Override
    public boolean has(ResourceLocation identifier) {
        return architectures.containsKey(identifier);
    }
}
