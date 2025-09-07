package tech.dlii.opencomputers.common.item;

import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;

public class EEPROMItem extends ComponentItem {
    public EEPROMItem(Properties properties) {
        super(SlotType.EEPROM, Tier.ONE, properties);
    }
}
