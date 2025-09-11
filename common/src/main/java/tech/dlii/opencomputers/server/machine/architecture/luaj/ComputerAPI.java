package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.LuaValue;
import li.cil.repack.org.luaj.vm2.lib.ZeroArgFunction;

public class ComputerAPI extends LuaJAPI {
    public ComputerAPI(LuaJLuaArchitecture owner) {
        super(owner);
    }

    @Override
    public void initialize() {
        LuaValue computer = LuaValue.tableOf();

        computer.set("realTime", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(System.currentTimeMillis() / 1000.0);
            }
        });

        computer.set("uptime", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(machine().upTime());
            }
        });

//        computer.set("tmpAddress", new ZeroArgFunction() {
//            @Override
//            public LuaValue call() {
//                return LuaValue.valueOf(machine().tmpAddress());
//            }
//        });

        lua().set("computer", computer);
    }
}
