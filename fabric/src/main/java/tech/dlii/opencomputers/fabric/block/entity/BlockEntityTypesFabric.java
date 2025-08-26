package tech.dlii.opencomputers.fabric.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import tech.dlii.opencomputers.block.Blocks;
import tech.dlii.opencomputers.block.entity.BlockEntityTypes;
import tech.dlii.opencomputers.block.entity.CaseBlockEntity;

public class BlockEntityTypesFabric extends BlockEntityTypes {

    public static void initialize() {
        CASE = register("case", () -> FabricBlockEntityTypeBuilder.create(CaseBlockEntity::new, Blocks.CASE.get()).build());

        BLOCK_ENTITY_TYPES.register();
    }
}
