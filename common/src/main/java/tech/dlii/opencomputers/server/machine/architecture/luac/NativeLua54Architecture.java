package tech.dlii.opencomputers.server.machine.architecture.luac;

import tech.dlii.opencomputers.api.machine.Architecture;

@Architecture.Name("Lua 5.4")
public class NativeLua54Architecture extends AbstractNativeLuaArchitecture {
    @Override
    protected LuaStateFactory factory() {
        return null;
    }
}
