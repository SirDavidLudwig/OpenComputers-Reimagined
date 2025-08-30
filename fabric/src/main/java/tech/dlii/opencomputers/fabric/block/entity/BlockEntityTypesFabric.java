package tech.dlii.opencomputers.fabric.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import tech.dlii.opencomputers.common.block.Blocks;
import tech.dlii.opencomputers.common.block.entity.BlockEntityTypes;
import tech.dlii.opencomputers.common.block.entity.CaseBlockEntity;

public class BlockEntityTypesFabric extends BlockEntityTypes {

    public static void initialize() {
        CASE = register("case", () -> FabricBlockEntityTypeBuilder.create(
                CaseBlockEntity::new,
                Blocks.CASE_1.get(),
                Blocks.CASE_2.get(),
                Blocks.CASE_3.get(),
                Blocks.CASE_CREATIVE.get()
        ).build());

        BLOCK_ENTITY_TYPES.register();
    }
}
