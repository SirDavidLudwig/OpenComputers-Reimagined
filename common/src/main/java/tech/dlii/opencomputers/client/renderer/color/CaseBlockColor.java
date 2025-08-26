package tech.dlii.opencomputers.client.renderer.color;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;
import tech.dlii.opencomputers.common.block.CaseBlock;

public class CaseBlockColor implements BlockColor {
    @Override
    public int getColor(net.minecraft.world.level.block.state.BlockState state, net.minecraft.world.level.BlockAndTintGetter level, net.minecraft.core.BlockPos pos, int tintIndex) {
        if (level == null || pos == null) {
            return 0;
        }
        final Block block = state.getBlock();
        if (block instanceof CaseBlock) {
            final int tier = ((CaseBlock) block).tier;
            switch (tier) {
                case 1:
                    return 0xC0C0C0; // Silver for tier 1
                case 2:
                    return 0xFFD700; // Gold for tier 2
                case 3:
                    return 0x00FFFF; // Cyan for tier 3
                case -1:
                    return 0xFF00FF; // Magenta for creative tier
            }
        }
        return 0;
    }
}
