package tech.dlii.opencomputers.server.machine.architecture.luaj;

import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.machine.Architecture;
import tech.dlii.opencomputers.api.machine.ExecutionResult;

public class LuaJLuaArchitecture implements Architecture {
    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public boolean recomputeMemory(Iterable<ItemStack> components) {
        return false;
    }

    @Override
    public boolean initialize() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public void runSynchronized() {

    }

    @Override
    public ExecutionResult runThreaded(boolean isSynchronizedReturn) {
        return null;
    }

    @Override
    public void onSignal() {

    }

    @Override
    public void onConnect() {

    }
}