package tech.dlii.opencomputers.neoforge.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import tech.dlii.opencomputers.block.Blocks;
import tech.dlii.opencomputers.block.entity.BlockEntityTypes;
import tech.dlii.opencomputers.block.entity.CaseBlockEntity;

public class BlockEntityTypesNeoForge extends BlockEntityTypes {
    public static void initialize() {
        CASE = register("case", () -> new BlockEntityType<>(CaseBlockEntity::new, Blocks.CASE.get()));

        BLOCK_ENTITY_TYPES.register();
    }
}
