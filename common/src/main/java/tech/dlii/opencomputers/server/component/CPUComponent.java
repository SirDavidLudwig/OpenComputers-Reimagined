package tech.dlii.opencomputers.server.component;

import tech.dlii.opencomputers.api.driver.DeviceInfo;

import java.util.Map;

public class CPUComponent implements DeviceInfo {

    int tier;
    private final Map<String, String> deviceInfo;

    public CPUComponent(int tier) {
        this.tier = tier;
        this.deviceInfo = Map.of(
                DeviceAttribute.Class, DeviceClass.Processor,
                DeviceAttribute.Description, "CPU",
                DeviceAttribute.Vendor, "",
                DeviceAttribute.Product, "",
                DeviceAttribute.Clock, "Clock speed"
        );
    }

    @Override
    public Map<String, String> getDeviceInfo() {
        return deviceInfo;
    }
}
