package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.Globals;
import tech.dlii.opencomputers.server.machine.architecture.AbstractArchitectureAPI;

public abstract class LuaJAPI extends AbstractArchitectureAPI {

    private LuaJLuaArchitecture owner;

    public LuaJAPI(LuaJLuaArchitecture owner) {
        super(owner.machine());
        this.owner = owner;
    }

    public LuaJLuaArchitecture owner() {
        return owner;
    }

    public Globals lua() {
        return owner.lua();
    }
}
