package tech.dlii.opencomputers.common.component;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;

import static net.minecraft.world.item.component.Consumables.defaultDrink;

public class Consumables {

    public static final Consumable ACID = defaultDrink().onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
            new MobEffectInstance(MobEffects.BLINDNESS, 200),
            new MobEffectInstance(MobEffects.POISON, 100),
            new MobEffectInstance(MobEffects.SLOWNESS, 600),
            new MobEffectInstance(MobEffects.NAUSEA, 1200),
            new MobEffectInstance(MobEffects.SATURATION, 2000)
    ))).build();

}
