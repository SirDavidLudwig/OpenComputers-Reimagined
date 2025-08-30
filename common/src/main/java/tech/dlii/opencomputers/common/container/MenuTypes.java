package tech.dlii.opencomputers.common.container;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.client.gui.screens.CaseScreen;

import java.util.function.Supplier;

public class MenuTypes {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(API.MOD_ID, Registries.MENU);

//    public static RegistrySupplier<MenuType<CaseMenu>> CASE = register("case", () -> new MenuType<>(CaseMenu::new, FeatureFlagSet.of()));
    public static RegistrySupplier<MenuType<CaseMenu>> CASE = register("case", () -> MenuRegistry.ofExtended(CaseMenu::create));

    public static void initialize() {
        MENU_TYPES.register();

        if (Platform.isFabric()) {
            ClientLifecycleEvent.CLIENT_STARTED.register(client -> {
                MenuRegistry.registerScreenFactory(CASE.get(), CaseScreen::new);
            });
        }
    }

    public static <T extends MenuType<?>> RegistrySupplier<T> register(String name, Supplier<T> menuType) {
        return MENU_TYPES.register(ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name), menuType);
    }
}
