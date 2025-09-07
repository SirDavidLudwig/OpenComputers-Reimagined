package tech.dlii.opencomputers.common.item;

import tech.dlii.opencomputers.api.driver.item.SlotType;

public class GPUItem extends ComponentItem {
    public GPUItem(int tier, Properties properties) {
        super(SlotType.CARD, tier, properties);
    }
}
