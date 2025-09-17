package tech.dlii.opencomputers.server.component;

import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.api.network.Visibility;

import java.util.Map;

public class EEPROMComponent extends AbstractComponent {

    public EEPROMComponent() {
        super(Map.of(
                DeviceAttribute.Class, DeviceClass.Processor,
                DeviceAttribute.Description, "CPU",
                DeviceAttribute.Vendor, "",
                DeviceAttribute.Product, "",
                DeviceAttribute.Clock, "Clock speed"
        ));
    }

    @Override
    protected Node initializeNode() {
        return API.network.newNode(this, Visibility.Neighbors).build();
    }

    //    public String checksum() {
//        return Hashing.crc32().hashBytes(data).toString();
//    }
}
