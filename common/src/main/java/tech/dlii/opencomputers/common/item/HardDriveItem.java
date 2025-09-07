package tech.dlii.opencomputers.common.item;

import tech.dlii.opencomputers.api.driver.item.SlotType;

public class HardDriveItem extends ComponentItem {
    public HardDriveItem(int tier, Properties properties) {
        super(SlotType.STORAGE, tier, properties);
    }
}
