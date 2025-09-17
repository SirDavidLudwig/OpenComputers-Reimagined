package tech.dlii.opencomputers.server.component;

import tech.dlii.opencomputers.api.driver.DeviceInfo;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.api.network.prefab.AbstractManagedEnvironment;

import java.util.Map;

public abstract class AbstractComponent extends AbstractManagedEnvironment implements DeviceInfo {

    private Node node;
    private final Map<String, String> deviceInfo;

    public AbstractComponent(Map<String, String> deviceInfo) {
        this.deviceInfo = deviceInfo;
        this.node = initializeNode();
    }

    protected abstract Node initializeNode();

    @Override
    public Node node() {
        return node;
    }

    protected void setNode(Node node) {
        this.node = node;
    }

    @Override
    public Map<String, String> getDeviceInfo() {
        return deviceInfo;
    }
}
