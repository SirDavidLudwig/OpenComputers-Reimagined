package tech.dlii.opencomputers.common.item;

import net.minecraft.world.item.Item;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;

public class ComponentItem extends Item {

    private final String slotType;
    private final int tier;

    public ComponentItem(Properties properties) {
        this(SlotType.NONE, Tier.NONE, properties);
    }

    public ComponentItem(String slotType, int tier, Properties properties) {
        super(properties);
        this.slotType = slotType;
        this.tier = tier;
    }

    public int tier() {
        return this.tier;
    }

    public String slotType() {
        return this.slotType;
    }
}
