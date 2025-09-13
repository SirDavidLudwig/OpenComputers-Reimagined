package tech.dlii.opencomputers.client.gui.fabric;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import tech.dlii.opencomputers.client.gui.CustomDataComponentTooltips;

public final class CustomDataComponentTooltipsImpl {
    public static void initialize() {
        ItemTooltipCallback.EVENT.register(CustomDataComponentTooltips::onItemTooltip);
    }
}
