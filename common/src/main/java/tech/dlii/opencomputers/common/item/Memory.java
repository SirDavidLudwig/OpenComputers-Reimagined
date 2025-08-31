package tech.dlii.opencomputers.common.item;

import tech.dlii.opencomputers.api.driver.item.SlotType;

public class Memory extends ComponentItem {
    public Memory(int tier, Properties properties) {
        super(SlotType.MEMORY, tier, properties);
    }
}
