package tech.dlii.opencomputers;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.common.item.Items;

public class CreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(API.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> COMMON = TABS.register(
            "creative_tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("category." + API.MOD_ID),
                    () -> new ItemStack(Items.CASE1)
            )
    );

    public static void initialize() {
        TABS.register();
        API.creativeTab = COMMON;
    }
}
