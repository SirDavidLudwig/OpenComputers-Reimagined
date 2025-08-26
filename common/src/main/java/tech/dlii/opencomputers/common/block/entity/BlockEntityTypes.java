package tech.dlii.opencomputers.common.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import tech.dlii.opencomputers.api.API;

import java.util.function.Supplier;

public class BlockEntityTypes {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(API.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static RegistrySupplier<BlockEntityType<CaseBlockEntity>> CASE;

    public static <T extends BlockEntityType<?>> RegistrySupplier<T> register(String name, Supplier<T> blockEntityType) {
        return BLOCK_ENTITY_TYPES.register(ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name), blockEntityType);
    }
}
