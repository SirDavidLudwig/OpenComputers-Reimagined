package tech.dlii.opencomputers.common.component;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import tech.dlii.opencomputers.api.API;

import java.util.function.UnaryOperator;

public class CustomDataComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(API.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<String>> ADDRESS = register(
            "address",
            builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));

    public static final RegistrySupplier<DataComponentType<ResourceLocation>> ARCHITECTURE = register(
            "architecture",
            builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));

    public static final RegistrySupplier<DataComponentType<Unit>> READ_ONLY = register(
            "read_only",
            builder -> builder.persistent(Unit.CODEC).networkSynchronized(Unit.STREAM_CODEC)
    ) ;

    public static void initialize() {
        DATA_COMPONENT_TYPES.register();
    }

    public static <T> RegistrySupplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPES.register(
                ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name),
                () -> builder.apply(DataComponentType.builder()).build()
        );
    }
}
