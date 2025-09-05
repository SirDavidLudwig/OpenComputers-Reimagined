package tech.dlii.opencomputers.client;

import net.minecraft.resources.ResourceLocation;
import tech.dlii.opencomputers.api.API;

public class Textures {

    public static final class GUI {
        public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/background.png");
        public static final ResourceLocation BORDERS = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/borders.png");
        public static final ResourceLocation COMPUTER = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/computer.png");
        public static final ResourceLocation POWER_BUTTON = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/button_power.png");
        public static final ResourceLocation SLOT = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/slot.png");
    }

    public static final class Icon {
        public static final ResourceLocation CARD = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/card.png");
        public static final ResourceLocation COMPONENT_BUS = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/component_bus.png");
        public static final ResourceLocation CONTAINER = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/container.png");
        public static final ResourceLocation CPU = ResourceLocation.fromNamespaceAndPath(API.MOD_ID,"textures/icon/cpu.png");
        public static final ResourceLocation EEPROM = ResourceLocation.fromNamespaceAndPath(API.MOD_ID,"textures/icon/eeprom.png");
        public static final ResourceLocation FLOPPY = ResourceLocation.fromNamespaceAndPath(API.MOD_ID,"textures/icon/floppy.png");
        public static final ResourceLocation HDD = ResourceLocation.fromNamespaceAndPath(API.MOD_ID,"textures/icon/hdd.png");
        public static final ResourceLocation MEMORY = ResourceLocation.fromNamespaceAndPath(API.MOD_ID,"textures/icon/memory.png");
        public static final ResourceLocation TIER_ONE = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/tier0.png");
        public static final ResourceLocation TIER_TWO = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/tier1.png");
        public static final ResourceLocation TIER_THREE = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/icon/tier2.png");
    }
}
