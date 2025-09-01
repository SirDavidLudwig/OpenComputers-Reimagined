package tech.dlii.opencomputers.common.item;

import net.minecraft.world.item.Item;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;

public class ComputerComponentItem extends Item {

    private final String slotType;
    private final int tier;

    public ComputerComponentItem(Properties properties) {
        this(SlotType.NONE, Tier.NONE, properties);
    }

    public ComputerComponentItem(String slotType, int tier, Properties properties) {
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
