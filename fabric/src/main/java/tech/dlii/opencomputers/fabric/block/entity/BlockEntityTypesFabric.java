package tech.dlii.opencomputers.fabric.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.block.entity.BlockEntityTypes;
import tech.dlii.opencomputers.common.block.entity.CaseBlockEntity;
import tech.dlii.opencomputers.common.block.entity.ScreenBlockEntity;

public class BlockEntityTypesFabric extends BlockEntityTypes {

    public static void initialize() {
        CASE = register("case", () -> FabricBlockEntityTypeBuilder.create(
                CaseBlockEntity::new,
                Blocks.CASE_TIER_1.get(),
                Blocks.CASE_TIER_2.get(),
                Blocks.CASE_TIER_3.get(),
                Blocks.CASE_CREATIVE.get()
        ).build());

        SCREEN = register("screen", () -> FabricBlockEntityTypeBuilder.create(
                ScreenBlockEntity::new,
                Blocks.SCREEN_TIER_1.get(),
                Blocks.SCREEN_TIER_2.get(),
                Blocks.SCREEN_TIER_3.get()
        ).build());

        BLOCK_ENTITY_TYPES.register();
    }
}
