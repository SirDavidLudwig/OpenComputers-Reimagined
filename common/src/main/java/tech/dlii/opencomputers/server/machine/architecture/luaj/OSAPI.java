package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.LuaValue;
import li.cil.repack.org.luaj.vm2.lib.ZeroArgFunction;

public class OSAPI extends LuaJAPI {
    public OSAPI(LuaJLuaArchitecture owner) {
        super(owner);
    }

    @Override
    public void initialize() {
        LuaValue os = LuaValue.tableOf();

        os.set("clock", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(System.currentTimeMillis() / 1000.0);
            }
        });

        lua().set("os", os);
    }
}
