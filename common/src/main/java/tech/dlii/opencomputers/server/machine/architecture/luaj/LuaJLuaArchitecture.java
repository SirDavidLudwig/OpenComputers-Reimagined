package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.*;
import li.cil.repack.org.luaj.vm2.lib.jse.JsePlatform;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.machine.architecture.Architecture;
import tech.dlii.opencomputers.api.machine.architecture.ExecutionResult;
import tech.dlii.opencomputers.api.machine.Machine;
import tech.dlii.opencomputers.api.machine.Signal;

import java.util.Arrays;
import java.util.stream.Stream;

public class LuaJLuaArchitecture implements Architecture {

    private Machine machine;

    private boolean initialized = false;
    private long memory = 0L;

    private Globals lua;
    private LuaFunction synchronizedCall = null;
    private LuaValue synchronizedResult = null;
    private LuaThread thread = null;

    private LuaJAPI[] apis;

    public LuaJLuaArchitecture(Machine machine) {
        this.machine = machine;
        apis = new LuaJAPI[] {
                new ComputerAPI(this),
                new OSAPI(this)
        };
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

        lua.set("dofile", LuaValue.NIL);
        lua.set("loadfile", LuaValue.NIL);

        for (LuaJAPI api : apis) {
            api.initialize();
        }

//        recomputeMemory(machine().host().internalComponents());
        OpenComputers.LOGGER.info("Creating the thread");
        LuaValue kernel = lua.load(Machine.class.getResourceAsStream("/assets/opencomputers/scripts/lua/machine.lua"), "=machine", "t", lua);
        thread = new LuaThread(lua, kernel);

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
    public void run() {
    synchronizedResult = synchronizedCall.call();
    synchronizedCall = null;
    }

    @Override
    public ExecutionResult runAsynchronous(boolean isSynchronizedReturn) {
        try {
            Varargs results;
            if (isSynchronizedReturn) {
                results = thread.resume(synchronizedResult);
                synchronizedResult = null;
            }
            else {
                if (!initialized) {
                    results = thread.resume(LuaValue.NONE);
                    // Mark as done *after* we ran, to avoid switching to synchronized
                    // calls when we actually need direct ones in the init phase.
                    initialized = true;
                    // We expect to get nothing here, if we do we had an error.
                    if (results.narg() > 0) {
                        // Fake zero sleep to avoid stopping if there are no signals.
                        OpenComputers.LOGGER.warn("Kernel returned unexpected results. " + results);
                        results = LuaValue.varargsOf(LuaValue.TRUE, LuaValue.valueOf(0));
                    }
                }
                else {
                    Signal signal = machine.popSignal();
                    if (signal != null) {
                        results = thread.resume(LuaValue.varargsOf(
                                Stream.concat(
                                        Stream.of(LuaValue.valueOf(signal.name())),
                                        Arrays.stream(signal.args()).map(LuaJCeorcion::toValue)).toArray(LuaValue[]::new)
                        ));
                    } else {
                        results = thread.resume(LuaValue.NONE);
                    }
                }
            }
            // Check if the kernel is still alive.
            if (thread.state.status != LuaThread.STATUS_SUSPENDED) {
                // If we get one function it must be a wrapper for a synchronized
                // call. The protocol is that a closure is pushed that is then called
                // from the main server thread, and returns a table, which is in turn
                // passed to the originating coroutine.yield().
                if (results.narg() == 2 && results.isfunction(2)) {
                    synchronizedCall = results.checkfunction(2);
                    return new ExecutionResult.SynchronizedCall();
                }
                // Check if we are shutting down, and if so if we're rebooting. This
                // is signalled by boolean values, where `false` means shut down,
                // `true` means reboot (i.e shutdown then start again).
                else if (results.narg() == 2 && results.type(2) == LuaValue.TBOOLEAN) {
                    return new ExecutionResult.Shutdown(results.toboolean(2));
                }
                else {
                    // If we have a single number, that's how long we may wait before
                    // resuming the state again. Note that the sleep may be interrupted
                    // early if a signal arrives in the meantime. If we have something
                    // else we just process the next signal or wait for one.
                    int ticks = (results.narg() == 2 && results.isnumber(2)) ? (int) (results.todouble(2) * 20) : Integer.MAX_VALUE;
                    return new ExecutionResult.Sleep(ticks);
                }
            }
            // The kernel thread returned. If it threw we'd be in the catch below.
            else {
                boolean isInnerError = results.type(2) == LuaValue.TBOOLEAN
                        && (results.isstring(3) || results.isnoneornil(3));
                boolean isOuterError = results.isstring(2) || results.isnoneornil(2);

                if (results.type(1) != LuaValue.TBOOLEAN || !isInnerError || !isOuterError) {
                    OpenComputers.LOGGER.warn("Kernel returned unexpected results.");
                }

                // The pcall *should* never return normally... but check for it nonetheless.
                if ((isOuterError && results.toboolean(1)) || (isInnerError && results.toboolean(2))) {
                    OpenComputers.LOGGER.warn("Kernel stopped unexpectedly.");
                    return new ExecutionResult.Shutdown(false);
                } else {
                    final String error;
                    if (isInnerError) {
                        if (results.isuserdata(3)) {
                            Object u = results.touserdata(3);
                            error = (u != null) ? u.toString() : null;
                        } else {
                            error = results.tojstring(3);
                        }
                    } else if (results.isuserdata(2)) {
                        Object u = results.touserdata(2);
                        error = (u != null) ? u.toString() : null;
                    } else {
                        error = results.tojstring(2);
                    }

                    if (error != null) {
                        return new ExecutionResult.Error(error);
                    } else {
                        return new ExecutionResult.Error("unknown error");
                    }
                }
            }
        } catch (LuaError e) {
            OpenComputers.LOGGER.error("Kernel crashed. This is a bug!", e);
            new ExecutionResult.Error("kernel panic: this is a bug, check your log file and report it!");
        } catch (Exception e) {
            OpenComputers.LOGGER.error("Unexpected error in kernel. This is a bug!", e);
            new ExecutionResult.Error("kernel panic: this is a bug, check your log file and report it!");
        }
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