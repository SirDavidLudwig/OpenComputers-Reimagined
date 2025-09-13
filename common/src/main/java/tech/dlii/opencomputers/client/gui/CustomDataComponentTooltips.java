package tech.dlii.opencomputers.client.gui;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import tech.dlii.opencomputers.common.component.CustomDataComponents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CustomDataComponentTooltips {

    public static final Map<RegistrySupplier<? extends DataComponentType<?>>, TooltipCallback<?>> CALLBACKS = new HashMap<>();

    static {
        register(CustomDataComponents.ADDRESS, (stack, tooltipComponents) -> {
            tooltipComponents.add(Component.literal(stack.get(CustomDataComponents.ADDRESS.get())));
        });

        register(CustomDataComponents.ARCHITECTURE, (stack, tooltipComponents) -> {
            ResourceLocation architecture = stack.get(CustomDataComponents.ARCHITECTURE.get());
            Component architectureName = Component.translatable(architecture.toLanguageKey("architecture"));
            tooltipComponents.add(Component.translatable("tooltip.opencomputers.architecture", architectureName));
        });
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static void onItemTooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag flag, List<Component> tooltipComponents) {
        CALLBACKS.forEach((componentType, callback) -> {
            if (stack.has(componentType.get())) {
                callback.apply(stack, context, flag, tooltipComponents);
            }
        });
    }

    private static void register(RegistrySupplier<? extends DataComponentType<?>> componentType, TooltipCallback<?> callback) {
        CALLBACKS.put(componentType, callback);
    }

    private static void register(RegistrySupplier<? extends DataComponentType<?>> componentType, TooltipMinimalCallback<?> callback) {
        register(componentType, (stack, context, flag, tooltipComponents) -> callback.apply(stack, tooltipComponents));
    }

    @ExpectPlatform
    public static void initialize() {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface TooltipCallback<T> {
        void apply(ItemStack stack, Item.TooltipContext context, TooltipFlag flag, List<Component> tooltipComponents);
    }

    @FunctionalInterface
    public interface TooltipMinimalCallback<T> {
        void apply(ItemStack stack, List<Component> tooltipComponents);
    }
}
