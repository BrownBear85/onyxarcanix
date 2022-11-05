package com.brownbear85.onyxarcanix.blocks.entities;

import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AltarBlockEntity extends ItemHolderBlockEntity {
    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ALTAR_BLOCK_ENTITY.get(), pos, state);
    }
}
