package tech.dlii.opencomputers.server.network.node;

import tech.dlii.opencomputers.api.machine.architecture.Callback;
import tech.dlii.opencomputers.api.machine.Context;
import tech.dlii.opencomputers.api.network.Environment;
import tech.dlii.opencomputers.api.network.Visibility;

import java.util.Collection;
import java.util.List;

public class ComponentNode extends Node implements tech.dlii.opencomputers.api.network.node.ComponentNode {
    public ComponentNode(Environment host, Visibility reachability) {
        super(host, reachability);
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public Visibility visibility() {
        return null;
    }

    @Override
    public void setVisibility(Visibility value) {

    }

    @Override
    public boolean canBeSeenFrom(tech.dlii.opencomputers.api.network.node.Node other) {
        return false;
    }

    @Override
    public Collection<String> methods() {
        return List.of();
    }

    @Override
    public Callback annotation(String method) {
        return null;
    }

    @Override
    public Object[] invoke(String method, Context context, Object... arguments) throws Exception {
        return new Object[0];
    }
}
