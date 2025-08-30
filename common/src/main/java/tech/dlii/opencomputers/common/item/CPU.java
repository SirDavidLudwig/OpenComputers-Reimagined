package tech.dlii.opencomputers.common.item;

import net.minecraft.world.item.Item;

public class CPU extends Item {

    private final int tier;

    public CPU(int tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    public int tier() {
        return tier;
    }
}
