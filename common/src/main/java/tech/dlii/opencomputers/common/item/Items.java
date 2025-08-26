package tech.dlii.opencomputers.common.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import tech.dlii.opencomputers.CreativeTabs;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.component.Consumables;

import java.util.function.Supplier;

public class Items {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(API.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> CASE1 = register(Blocks.CASE1.getId().getPath(), () -> new BlockItem(Blocks.CASE1.get(), baseProperties(Blocks.CASE1.getId().getPath())));
    public static final RegistrySupplier<Item> CASE2 = register(Blocks.CASE2.getId().getPath(), () -> new BlockItem(Blocks.CASE2.get(), baseProperties(Blocks.CASE2.getId().getPath())));
    public static final RegistrySupplier<Item> CASE3 = register(Blocks.CASE3.getId().getPath(), () -> new BlockItem(Blocks.CASE3.get(), baseProperties(Blocks.CASE3.getId().getPath())));
    public static final RegistrySupplier<Item> CASE_CREATIVE = register(Blocks.CASE_CREATIVE.getId().getPath(), () -> new BlockItem(Blocks.CASE_CREATIVE.get(), baseProperties(Blocks.CASE_CREATIVE.getId().getPath())));

    // Items
    public static final RegistrySupplier<Item> ACID = register("acid", () -> new Item(baseProperties("acid").component(DataComponents.CONSUMABLE, Consumables.ACID)));
    public static final RegistrySupplier<Item> ALU = register("alu", () -> new Item(baseProperties("alu")));
    public static final RegistrySupplier<Item> ARROW_KEYS = register("arrow_keys", () -> new Item(baseProperties("arrow_keys")));
    public static final RegistrySupplier<Item> BUTTON_GROUP = register("button_group", () -> new Item(baseProperties("button_group")));
    public static final RegistrySupplier<Item> CARD_BASE = register("card_base", () -> new Item(baseProperties("card_base")));
    public static final RegistrySupplier<Item> CONTROL_UNIT = register("control_unit", () -> new Item(baseProperties("control_unit")));
    public static final RegistrySupplier<Item> CUTTING_WIRE = register("cutting_wire", () -> new Item(baseProperties("cutting_wire")));

    public static final RegistrySupplier<Item> DIAMOND_NUGGET = register("diamond_nugget", () -> new Item(baseProperties("diamond_nugget")));
    public static final RegistrySupplier<Item> DISK = register("disk", () -> new Item(baseProperties("disk")));
    public static final RegistrySupplier<Item> INTERWEB = register("interweb", () -> new Item(baseProperties("interweb")));

    public static final RegistrySupplier<Item> MEMORY1 = register("memory1", () -> new Item(baseProperties("memory1")));
    public static final RegistrySupplier<Item> MEMORY2 = register("memory2", () -> new Item(baseProperties("memory2")));
    public static final RegistrySupplier<Item> MEMORY3 = register("memory3", () -> new Item(baseProperties("memory3")));
    public static final RegistrySupplier<Item> MEMORY4 = register("memory4", () -> new Item(baseProperties("memory4")));
    public static final RegistrySupplier<Item> MEMORY5 = register("memory5", () -> new Item(baseProperties("memory5")));
    public static final RegistrySupplier<Item> MEMORY6 = register("memory6", () -> new Item(baseProperties("memory6")));

    public static final RegistrySupplier<Item> MICROCHIP1 = register("microchip1", () -> new Item(baseProperties("microchip1")));
    public static final RegistrySupplier<Item> MICROCHIP2 = register("microchip2", () -> new Item(baseProperties("microchip2")));
    public static final RegistrySupplier<Item> MICROCHIP3 = register("microchip3", () -> new Item(baseProperties("microchip3")));

    public static final RegistrySupplier<Item> NUMPAD = register("numpad", () -> new Item(baseProperties("numpad")));
    public static final RegistrySupplier<Item> PRINTED_CIRCUIT_BOARD = register("printed_circuit_board", () -> new Item(baseProperties("printed_circuit_board")));
    public static final RegistrySupplier<Item> RAW_CIRCUIT_BOARD = register("raw_circuit_board", () -> new Item(baseProperties("raw_circuit_board")));
    public static final RegistrySupplier<Item> TRANSISTOR = register("transistor", () -> new Item(baseProperties("transistor")));

    public static void initialize() {
        ITEMS.register();
    }

    public static Item.Properties baseProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name))).arch$tab(CreativeTabs.COMMON);
    }

//    public static Item.Properties

    public static <T extends Item> RegistrySupplier<Item> register(String name, Supplier<T> item) {
        OpenComputers.LOGGER.info("Registering item: " + name + "; Resource Location: " + ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name));
        return ITEMS.register(ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name), item);
    }
}
