package tech.dlii.opencomputers.server.machine;

import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.machine.Machine;
import tech.dlii.opencomputers.api.machine.MachineAPI;
import tech.dlii.opencomputers.api.machine.MachineHost;

import java.util.concurrent.ScheduledExecutorService;

public final class Machines implements MachineAPI {

    ScheduledExecutorService threadPool = ThreadPoolFactory.create("Computer", 1);

    @Override
    public Machine create(MachineHost host) {
        return new tech.dlii.opencomputers.server.machine.Machine(host);
    }

    public static void initialize() {
        API.machine = new Machines();
        LifecycleEvent.SERVER_BEFORE_START.register(ThreadPoolFactory::onServerBeforeStart);
        LifecycleEvent.SERVER_STOPPED.register(ThreadPoolFactory::onServerStopped);
    }
}
