package tech.dlii.opencomputers.server.network;

import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.network.Environment;
import tech.dlii.opencomputers.api.network.NetworkAPI;
import tech.dlii.opencomputers.api.network.Visibility;
import tech.dlii.opencomputers.server.network.node.Node;

public class Networks implements NetworkAPI {
    public static void initialize() {
        API.network = new Networks();
    }

    @Override
    public tech.dlii.opencomputers.api.network.node.Node.Builder newNode(Environment host, Visibility reachability) {
        return new Node.Builder(host, reachability);
    }
}
