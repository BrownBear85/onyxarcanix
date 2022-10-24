package com.brownbear85.onyxarcanix.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WorldActions {

    /**
     * Plays a sound with default values and SoundSource = player
     * @param entity Entity to play sound at
     * @param sound SoundEvent to play
     */
    public static void playSound(Entity entity, SoundEvent sound) {
        entity.getLevel().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 1.0F, 1.0F, false);
    }

    /**
     * Replace blocks in a cubical area around a center point.
     * @param level the level to do replace blocks in
     * @param pos the center of the cube
     * @param set blockstate to set blocks to
     * @param replace block to replace
     * @param distance size of the cube (distance from center) - 0 = 1x1, 1 = 3x3, 2 = 5x5, etc.
     * @return if any blocks were replaced
     */
    public static boolean replaceNear(Level level, BlockPos pos, BlockState set, Block replace, int distance) {
        int x = Math.round(pos.getX()) - distance;
        int y = Math.round(pos.getY()) - distance;
        int z = Math.round(pos.getZ()) - distance;

        boolean replaced = false;
        BlockPos workPos;
        for (int i = 0; i < distance * 2 + 1; i++) {
            for (int j = 0; j < distance * 2 + 1; j++) {
                for (int k = 0; k < distance * 2 + 1; k++) {
                    workPos = new BlockPos(x + i, y + j, z + k);
                    if (level.getBlockState(workPos).is(replace)) {
                        level.setBlockAndUpdate(workPos, set);
                        replaced = true;
                    }
                }
            }
        }
        return replaced;
    }
}
