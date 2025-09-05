package tech.dlii.opencomputers.server;

import net.minecraft.world.level.GameRules;


public class OpenComputersGameRules
{
    public static final GameRules.Key<GameRules.BooleanValue> COMPUTERS_USE_POWER;

    static {
        COMPUTERS_USE_POWER = GameRules.register("computersUsePower", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
    }

    public static void initialize() {}
}
