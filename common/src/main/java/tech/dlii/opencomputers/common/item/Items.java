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
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.component.Consumables;

import java.util.function.Supplier;

public class Items {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(API.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> CASE_TIER_1 = register(Blocks.CASE_TIER_1.getId().getPath(), () -> new BlockItem(Blocks.CASE_TIER_1.get(), baseProperties(Blocks.CASE_TIER_1.getId().getPath())));
    public static final RegistrySupplier<Item> CASE_TIER_2 = register(Blocks.CASE_TIER_2.getId().getPath(), () -> new BlockItem(Blocks.CASE_TIER_2.get(), baseProperties(Blocks.CASE_TIER_2.getId().getPath())));
    public static final RegistrySupplier<Item> CASE_TIER_3 = register(Blocks.CASE_TIER_3.getId().getPath(), () -> new BlockItem(Blocks.CASE_TIER_3.get(), baseProperties(Blocks.CASE_TIER_3.getId().getPath())));
    public static final RegistrySupplier<Item> CASE_CREATIVE = register(Blocks.CASE_CREATIVE.getId().getPath(), () -> new BlockItem(Blocks.CASE_CREATIVE.get(), baseProperties(Blocks.CASE_CREATIVE.getId().getPath())));

    public static final RegistrySupplier<Item> SCREEN_TIER_1 = register(Blocks.SCREEN_TIER_1.getId().getPath(), () -> new BlockItem(Blocks.SCREEN_TIER_1.get(), baseProperties(Blocks.SCREEN_TIER_1.getId().getPath())));
    public static final RegistrySupplier<Item> SCREEN_TIER_2 = register(Blocks.SCREEN_TIER_2.getId().getPath(), () -> new BlockItem(Blocks.SCREEN_TIER_2.get(), baseProperties(Blocks.SCREEN_TIER_2.getId().getPath())));
    public static final RegistrySupplier<Item> SCREEN_TIER_3 = register(Blocks.SCREEN_TIER_3.getId().getPath(), () -> new BlockItem(Blocks.SCREEN_TIER_3.get(), baseProperties(Blocks.SCREEN_TIER_3.getId().getPath())));

    // Items
    public static final RegistrySupplier<Item> ACID = register("acid", () -> new Item(baseProperties("acid").component(DataComponents.CONSUMABLE, Consumables.ACID)));
    public static final RegistrySupplier<Item> ALU = register("alu", () -> new Item(baseProperties("alu")));
    public static final RegistrySupplier<Item> ARROW_KEYS = register("arrow_keys", () -> new Item(baseProperties("arrow_keys")));
    public static final RegistrySupplier<Item> BUTTON_GROUP = register("button_group", () -> new Item(baseProperties("button_group")));
    public static final RegistrySupplier<Item> CARD_BASE = register("card_base", () -> new Item(baseProperties("card_base")));
    public static final RegistrySupplier<Item> CPU_TIER_1 = register("cpu_0", () -> new CPUItem(Tier.ONE, baseProperties("cpu_0")));
    public static final RegistrySupplier<Item> CPU_TIER_2 = register("cpu_1", () -> new CPUItem(Tier.TWO, baseProperties("cpu_1")));
    public static final RegistrySupplier<Item> CPU_TIER_3 = register("cpu_2", () -> new CPUItem(Tier.THREE, baseProperties("cpu_2")));
    public static final RegistrySupplier<Item> CONTROL_UNIT = register("control_unit", () -> new Item(baseProperties("control_unit")));
    public static final RegistrySupplier<Item> CUTTING_WIRE = register("cutting_wire", () -> new Item(baseProperties("cutting_wire")));

    public static final RegistrySupplier<Item> DIAMOND_NUGGET = register("diamond_nugget", () -> new Item(baseProperties("diamond_nugget")));
    public static final RegistrySupplier<Item> DISK = register("disk", () -> new Item(baseProperties("disk")));

    public static final RegistrySupplier<Item> FLOPPY = register("floppy", () -> new FloppyItem(baseProperties("floppy")));
    public static final RegistrySupplier<Item> GPU_TIER_1 = register("gpu_0", () -> new GPUItem(Tier.ONE, baseProperties("gpu_0")));
    public static final RegistrySupplier<Item> GPU_TIER_2 = register("gpu_1", () -> new GPUItem(Tier.TWO, baseProperties("gpu_1")));
    public static final RegistrySupplier<Item> GPU_TIER_3 = register("gpu_2", () -> new GPUItem(Tier.THREE, baseProperties("gpu_2")));

    public static final RegistrySupplier<Item> INTERWEB = register("interweb", () -> new Item(baseProperties("interweb")));

    public static final RegistrySupplier<Item> HDD_TIER_1 = register("hdd_0", () -> new HardDriveItem(Tier.ONE, baseProperties("hdd_0")));
    public static final RegistrySupplier<Item> HDD_TIER_2 = register("hdd_1", () -> new HardDriveItem(Tier.TWO, baseProperties("hdd_1")));
    public static final RegistrySupplier<Item> HDD_TIER_3 = register("hdd_2", () -> new HardDriveItem(Tier.THREE, baseProperties("hdd_2")));

    public static final RegistrySupplier<Item> MEMORY1 = register("memory1", () -> new MemoryItem(Tier.ONE, baseProperties("memory1")));
    public static final RegistrySupplier<Item> MEMORY2 = register("memory2", () -> new MemoryItem(Tier.ONE, baseProperties("memory2")));
    public static final RegistrySupplier<Item> MEMORY3 = register("memory3", () -> new MemoryItem(Tier.TWO, baseProperties("memory3")));
    public static final RegistrySupplier<Item> MEMORY4 = register("memory4", () -> new MemoryItem(Tier.TWO, baseProperties("memory4")));
    public static final RegistrySupplier<Item> MEMORY5 = register("memory5", () -> new MemoryItem(Tier.THREE, baseProperties("memory5")));
    public static final RegistrySupplier<Item> MEMORY6 = register("memory6", () -> new MemoryItem(Tier.THREE, baseProperties("memory6")));

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

    public static <T extends Item> RegistrySupplier<Item> register(String name, Supplier<T> item) {
        return ITEMS.register(ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name), item);
    }
}
