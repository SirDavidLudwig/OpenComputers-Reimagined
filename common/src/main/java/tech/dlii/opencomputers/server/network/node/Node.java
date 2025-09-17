package tech.dlii.opencomputers.server.network.node;

import tech.dlii.opencomputers.api.network.Environment;
import tech.dlii.opencomputers.api.network.Network;
import tech.dlii.opencomputers.api.network.Visibility;

public class Node implements tech.dlii.opencomputers.api.network.node.Node {

    Environment host;
    Visibility reachability;

    String address;
    Network network = null;

    public Node(Environment host, Visibility reachability) {
        this.host = host;
        this.reachability = reachability;
    }

    @Override
    public Environment host() {
        return this.host;
    }

    @Override
    public Visibility reachability() {
        return null;
    }

    @Override
    public String address() {
        return this.address;
    }

    @Override
    public Network network() {
        return this.network;
    }

    @Override
    public boolean isNeighborOf(tech.dlii.opencomputers.api.network.node.Node other) {
        return false;
    }

    @Override
    public boolean canBeReachedFrom(tech.dlii.opencomputers.api.network.node.Node other) {
        return false;
    }

    public static class Builder implements tech.dlii.opencomputers.api.network.node.Node.Builder {

        private final Environment host;
        private final Visibility reachability;

        public Builder(Environment host, Visibility reachability) {
            this.host = host;
            this.reachability = reachability;
        }

        @Override
        public Node build() {
            return new Node(host, reachability);
        }
    }
}
