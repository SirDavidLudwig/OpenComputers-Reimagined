package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.*;
import li.cil.repack.org.luaj.vm2.lib.jse.JsePlatform;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.machine.Architecture;
import tech.dlii.opencomputers.api.machine.ExecutionResult;
import tech.dlii.opencomputers.api.machine.Machine;
import tech.dlii.opencomputers.api.machine.Signal;

import java.util.stream.Stream;

public class LuaJLuaArchitecture implements Architecture {

    private Machine machine;

    private boolean initialized = false;
    private long memory = 0L;

    private Globals lua;
    private LuaFunction synchronizedCall = null;
    private LuaValue synchronizedResult = null;
    private LuaThread thread = null;

    private LuaJAPI[] apis = new LuaJAPI[] {
        new ComputerAPI(this)
    };

    public LuaJLuaArchitecture(Machine machine) {
        this.machine = machine;
    }

    @Override
    public boolean initialize() {
        // Create the VM
        lua = JsePlatform.debugGlobals();

        // Remove undesired builtins
        lua.set("package", LuaValue.NIL);
        lua.set("require", LuaValue.NIL);
        lua.set("io", LuaValue.NIL);
        lua.set("os", LuaValue.NIL);
        lua.set("luajava", LuaValue.NIL);

        for (LuaJAPI api : apis) {
            api.initialize();
        }

//        recomputeMemory(machine().host().internalComponents());

//        LuaFunction kernel = lua.load(Machine.class.getResourceAsStream("/assets/opencomputers/scripts/lua/machine.lua"));
//        thread = new LuaThread(lua, kernel);

        return true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public boolean recomputeMemory(Iterable<ItemStack> components) {
//        memory = memoryInBytes(components);
        return memory > 0;
    }

    @Override
    public void close() {
        lua = null;
        thread = null;
        synchronizedCall = null;
        synchronizedResult = null;
        initialized = false;
    }

    @Override
    public void runSynchronized() {
    synchronizedResult = synchronizedCall.call();
    synchronizedCall = null;
    }

    @Override
    public ExecutionResult runThreaded(boolean isSynchronizedReturn) {
//        try {
//            Varargs results;
//            if (isSynchronizedReturn) {
//                results = thread.resume(synchronizedResult);
//                synchronizedResult = null;
//            }
//            else {
//                if (!initialized) {
//                    results = thread.resume(LuaValue.NONE);
//                    // Mark as done *after* we ran, to avoid switching to synchronized
//                    // calls when we actually need direct ones in the init phase.
//                    initialized = true;
//                    // We expect to get nothing here, if we do we had an error.
//                    if (results.narg() > 0) {
//                        // Fake zero sleep to avoid stopping if there are no signals.
//                        results = LuaValue.varargsOf(LuaValue.TRUE, LuaValue.valueOf(0));
//                    }
//                }
//                else {
//                    Signal signal = machine.popSignal();
//                    if (signal != null) {
//                        Stream.concat(
//                                Stream.of(LuaValue.valueOf(signal.name())),
//                                Stream.of(signal.args()).map(LuaJLuaArchitecture::toLuaValue)
//                        ).toArray(LuaValue[]::new);
//                        thread.resume(LuaValue.)
//                    }
//                }
//            }
//        }
        return null;
    }

    @Override
    public void onSignal() {

    }

    @Override
    public void onConnect() {

    }

    public Globals lua() {
        return this.lua;
    }

    public Machine machine() {
        return this.machine;
    }
}