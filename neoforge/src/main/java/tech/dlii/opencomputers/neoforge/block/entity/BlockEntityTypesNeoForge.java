package tech.dlii.opencomputers.neoforge.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.block.entity.BlockEntityTypes;
import tech.dlii.opencomputers.common.block.entity.CaseBlockEntity;
import tech.dlii.opencomputers.common.block.entity.ScreenBlockEntity;

public class BlockEntityTypesNeoForge extends BlockEntityTypes {
    public static void initialize() {
        CASE = register("case", () -> new BlockEntityType<>(
                CaseBlockEntity::new,
                Blocks.CASE_TIER_1.get(),
                Blocks.CASE_TIER_2.get(),
                Blocks.CASE_TIER_3.get(),
                Blocks.CASE_CREATIVE.get()
        ));

        SCREEN = register("screen", () -> new BlockEntityType<>(
                ScreenBlockEntity::new,
                Blocks.SCREEN_TIER_1.get(),
                Blocks.SCREEN_TIER_2.get(),
                Blocks.SCREEN_TIER_3.get()
        ));

        BLOCK_ENTITY_TYPES.register();
    }
}
