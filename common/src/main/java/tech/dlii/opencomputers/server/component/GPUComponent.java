package tech.dlii.opencomputers.server.component;

import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.api.network.Visibility;

import java.util.Map;

public class GPUComponent extends AbstractComponent {

    private final int tier;

    public GPUComponent(int tier) {
        super(Map.of(
                DeviceAttribute.Class, DeviceClass.Display,
                DeviceAttribute.Description, "GPU",
                DeviceAttribute.Vendor, "",
                DeviceAttribute.Product, "",
                DeviceAttribute.Clock, "Clock speed"
        ));
        this.tier = tier;
    }

    @Override
    protected Node initializeNode() {
        return API.network.newNode(this, Visibility.Neighbors).build();
    }
}
