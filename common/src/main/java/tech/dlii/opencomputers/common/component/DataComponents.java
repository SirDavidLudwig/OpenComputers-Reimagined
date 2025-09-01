package tech.dlii.opencomputers.common.component;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import tech.dlii.opencomputers.api.API;

import java.util.function.UnaryOperator;

public class DataComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(API.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<ArchitectureComponent>> ARCHITECTURE = register(
            "architecture",
            builder -> builder.persistent(ArchitectureComponent.CODEC).networkSynchronized(ArchitectureComponent.STREAM_CODEC));

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
