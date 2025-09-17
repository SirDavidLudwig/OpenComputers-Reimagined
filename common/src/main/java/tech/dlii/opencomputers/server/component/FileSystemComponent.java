package tech.dlii.opencomputers.server.component;

import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.api.network.Visibility;

import java.util.Map;

public class FileSystemComponent extends AbstractComponent {
    public FileSystemComponent() {
        super(Map.of(
                DeviceAttribute.Class, DeviceClass.Volume,
                DeviceAttribute.Description, "Filesystem",
                DeviceAttribute.Vendor, "",
                DeviceAttribute.Product, "MPFS.21.6"
//                DeviceAttribute.Capacity -> (fileSystem.spaceTotal * 1.024).toInt.toString,
//                DeviceAttribute.Size -> fileSystem.spaceTotal.toString,
//                DeviceAttribute.Clock -> (((2000 / readCosts(speed)).toInt / 100).toString + "/" + ((2000 / seekCosts(speed)).toInt / 100).toString + "/" + ((2000 / writeCosts(speed)).toInt / 100).toString)
        ));
    }

    @Override
    protected Node initializeNode() {
        return API.network.newNode(this, Visibility.Neighbors).build();
    }
}
