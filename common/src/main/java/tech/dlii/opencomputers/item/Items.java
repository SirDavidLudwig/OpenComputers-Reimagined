package tech.dlii.opencomputers.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.Logger;
import tech.dlii.opencomputers.CreativeTabs;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.block.Blocks;

import java.util.function.Supplier;

public class Items {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(API.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> CASE = ITEMS.register(Blocks.CASE.getId().getPath(), () -> new BlockItem(Blocks.CASE.get(), baseProperties(Blocks.CASE.getId().getPath())));

    public static void initialize() {
        ITEMS.register();
    }

    public static Item.Properties baseProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name))).arch$tab(CreativeTabs.COMMON);
    }

    public static RegistrySupplier<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name), item);
    }
}
