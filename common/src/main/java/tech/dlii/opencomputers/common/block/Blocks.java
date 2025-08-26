package tech.dlii.opencomputers.common.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import tech.dlii.opencomputers.api.API;

import java.util.function.Supplier;

public class Blocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(API.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<CaseBlock> CASE1 = register("case1", () -> new CaseBlock(1, baseProperties("case").mapColor(MapColor.METAL).sound(SoundType.METAL)));
    public static final RegistrySupplier<CaseBlock> CASE2 = register("case2", () -> new CaseBlock(2, baseProperties("case").mapColor(MapColor.METAL).sound(SoundType.METAL)));
    public static final RegistrySupplier<CaseBlock> CASE3 = register("case3", () -> new CaseBlock(3, baseProperties("case").mapColor(MapColor.METAL).sound(SoundType.METAL)));
    public static final RegistrySupplier<CaseBlock> CASE_CREATIVE = register("case_creative", () -> new CaseBlock(-1, baseProperties("case").mapColor(MapColor.METAL).sound(SoundType.METAL)));

    public static void initialize() {
        BLOCKS.register();
    }

    public static <T extends Block> RegistrySupplier<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static BlockBehaviour.Properties baseProperties(String name) {
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name)));
    }
}

