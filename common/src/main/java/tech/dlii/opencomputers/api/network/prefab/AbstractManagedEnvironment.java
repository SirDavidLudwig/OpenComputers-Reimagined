package tech.dlii.opencomputers.api.network.prefab;

import tech.dlii.opencomputers.api.network.ManagedEnvironment;
import tech.dlii.opencomputers.api.network.node.Node;

public abstract class AbstractManagedEnvironment implements ManagedEnvironment {

    private Node node;

    @Override
    public Node node() {
        return node;
    }

    protected void setNode(Node node) {
        this.node = node;
    }
}
