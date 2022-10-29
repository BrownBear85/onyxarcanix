package com.brownbear85.onyxarcanix.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class Pedestal extends BaseItemHolder {
    public Pedestal(Properties properties, float itemX, float itemY, float itemZ) {
        super(properties, itemX, itemY, itemZ);
    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        super.tick(state, serverLevel, pos, random);
    }
}
