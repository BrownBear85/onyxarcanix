package com.brownbear85.onyxarcanix.blocks.entities;

import com.brownbear85.onyxarcanix.blocks.BaseItemHolderBlock;
import com.brownbear85.onyxarcanix.blocks.PedestalBlock;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import com.brownbear85.onyxarcanix.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntity extends ItemHolderBlockEntity {
    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.PEDESTAL_BLOCK_ENTITY.get(), pos, state);
    }
}
