package tech.dlii.opencomputers.common.item;

import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;

public class FloppyItem extends ComponentItem {
    public FloppyItem(Properties properties) {
        super(SlotType.FLOPPY, Tier.ONE, properties);
    }
}
