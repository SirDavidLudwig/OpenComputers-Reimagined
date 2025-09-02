package tech.dlii.opencomputers.common.item;

import tech.dlii.opencomputers.api.driver.item.SlotType;

public class MemoryItem extends ComputerComponentItem {
    public MemoryItem(int tier, Properties properties) {
        super(SlotType.MEMORY, tier, properties);
    }
}
