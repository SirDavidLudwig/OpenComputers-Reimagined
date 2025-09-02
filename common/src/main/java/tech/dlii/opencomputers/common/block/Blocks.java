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
import tech.dlii.opencomputers.api.Tier;

import java.util.function.Supplier;

public class Blocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(API.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<CaseBlock> CASE_TIER_1 = register("case_" + Tier.ONE, () -> new CaseBlock(Tier.ONE, baseProperties("case").mapColor(MapColor.METAL).sound(SoundType.METAL).strength(0.5f, 6.0f)));
    public static final RegistrySupplier<CaseBlock> CASE_TIER_2 = register("case_" + Tier.TWO, () -> new CaseBlock(Tier.TWO, baseProperties("case").mapColor(MapColor.METAL).sound(SoundType.METAL).strength(0.5f, 6.0f)));
    public static final RegistrySupplier<CaseBlock> CASE_TIER_3 = register("case_" + Tier.THREE, () -> new CaseBlock(Tier.THREE, baseProperties("case").mapColor(MapColor.METAL).sound(SoundType.METAL).strength(0.5f, 6.0f)));
    public static final RegistrySupplier<CaseBlock> CASE_CREATIVE = register("case_creative", () -> new CaseBlock(Tier.FOUR, baseProperties("case").mapColor(MapColor.METAL).sound(SoundType.METAL).strength(0.5f, 6.0f)));
    public static final RegistrySupplier<ScreenBlock> SCREEN_TIER_1 = register("screen_" + Tier.ONE, () -> new ScreenBlock(Tier.ONE, baseProperties("screen").mapColor(MapColor.METAL).sound(SoundType.METAL).strength(0.5f, 6.0f)));
    public static final RegistrySupplier<ScreenBlock> SCREEN_TIER_2 = register("screen_" + Tier.TWO, () -> new ScreenBlock(Tier.TWO, baseProperties("screen").mapColor(MapColor.METAL).sound(SoundType.METAL).strength(0.5f, 6.0f)));
    public static final RegistrySupplier<ScreenBlock> SCREEN_TIER_3 = register("screen_" + Tier.THREE, () -> new ScreenBlock(Tier.THREE, baseProperties("screen").mapColor(MapColor.METAL).sound(SoundType.METAL).strength(0.5f, 6.0f)));

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

