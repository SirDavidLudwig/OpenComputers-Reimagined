package tech.dlii.opencomputers.server.component;

import tech.dlii.opencomputers.api.driver.DeviceInfo;
import tech.dlii.opencomputers.common.config.Configuration;

import java.util.Map;

public class MemoryComponent implements DeviceInfo {

    int tier;
    private final Map<String, String> deviceInfo;

    public MemoryComponent(int tier) {
        this.tier = tier;
        int x = ((int) Configuration.CALL_BUDGETS[tier] * 1000);
        this.deviceInfo = Map.of(
                DeviceAttribute.Class, DeviceClass.Processor,
                DeviceAttribute.Description, "Memory bank",
                DeviceAttribute.Vendor, "",
                DeviceAttribute.Product, "",
                DeviceAttribute.Clock, Integer.toString((int) Configuration.CALL_BUDGETS[tier] * 1000)
        );
    }

    @Override
    public Map<String, String> getDeviceInfo() {
        return Map.of();
    }
}
