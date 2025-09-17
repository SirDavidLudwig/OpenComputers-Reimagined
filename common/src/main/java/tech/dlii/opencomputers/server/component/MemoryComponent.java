package tech.dlii.opencomputers.server.component;

import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.api.network.Visibility;
import tech.dlii.opencomputers.common.config.Configuration;

import java.util.Map;

public class MemoryComponent extends AbstractComponent {

    private final int tier;

    public MemoryComponent(int tier) {
        super(Map.of(
                DeviceAttribute.Class, DeviceClass.Processor,
                DeviceAttribute.Description, "Memory bank",
                DeviceAttribute.Vendor, "",
                DeviceAttribute.Product, "",
                DeviceAttribute.Clock, Integer.toString((int) Configuration.CALL_BUDGETS[tier] * 1000)
        ));
        this.tier = tier;
    }

    @Override
    protected Node initializeNode() {
        return API.network.newNode(this, Visibility.Neighbors).build();
    }
}
