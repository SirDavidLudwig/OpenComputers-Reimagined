package tech.dlii.opencomputers.client.renderer.color;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.common.block.ScreenBlock;

public class ScreenBlockColor implements BlockColor {
    @Override
    public int getColor(net.minecraft.world.level.block.state.BlockState state, net.minecraft.world.level.BlockAndTintGetter level, net.minecraft.core.BlockPos pos, int tintIndex) {
        if (level == null || pos == null) {
            return 0;
        }
        final Block block = state.getBlock();
        if (block instanceof ScreenBlock screenBlock) {
            switch (screenBlock.tier()) {
                case Tier.ONE:
                    return 0xC0C0C0; // Silver for tier 1
                case Tier.TWO:
                    return 0xFFD700; // Gold for tier 2
                case Tier.THREE:
                    return 0x00FFFF; // Cyan for tier 3
            }
        }
        return 0;
    }
}
