package tech.dlii.opencomputers.common.item;

import tech.dlii.opencomputers.api.driver.item.SlotType;

public class CPU extends ComponentItem {
    public CPU(int tier, Properties properties) {
        super(SlotType.CPU, tier, properties);
    }
}
