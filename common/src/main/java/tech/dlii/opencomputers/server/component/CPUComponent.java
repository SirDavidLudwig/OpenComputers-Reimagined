package tech.dlii.opencomputers.server.component;

import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.api.network.Visibility;

import java.util.Map;

public class CPUComponent extends AbstractComponent {

    public CPUComponent(int tier) {
        super(Map.of(
                DeviceAttribute.Class, DeviceClass.Processor,
                DeviceAttribute.Description, "CPU",
                DeviceAttribute.Vendor, "",
                DeviceAttribute.Product, "FlixiArch " + (tier + 1) + " Processor",
                DeviceAttribute.Clock, "Clock speed"
        ));
    }

    @Override
    protected Node initializeNode() {
        return API.network.newNode(this, Visibility.Neighbors).build();
    }
}
