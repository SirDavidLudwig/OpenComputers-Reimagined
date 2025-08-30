package tech.dlii.opencomputers.client;

import net.minecraft.resources.ResourceLocation;
import tech.dlii.opencomputers.api.API;

public class Textures {

    public static final class GUI {
        public static final ResourceLocation POWER_BUTTON = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/button_power.png");
        public static final ResourceLocation SLOT = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/slot.png");
    }

    public static final class Icon {
        public static final ResourceLocation CPU = ResourceLocation.fromNamespaceAndPath(API.MOD_ID,"textures/icon/cpu.png");
        public static final ResourceLocation TIER_ONE = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/tier0.png");
        public static final ResourceLocation TIER_TWO = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/tier1.png");
        public static final ResourceLocation TIER_THREE = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/tier2.png");
    }
}
