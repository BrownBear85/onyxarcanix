package com.brownbear85.onyxarcanix.util;

import com.brownbear85.onyxarcanix.init.BlockInit;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class EntityRituals {
    public static void doEntityDeathRitual(Entity entity) {
        if (entity.getType().equals(EntityType.COW)) {
            animalDeathOnyxCreationRitual(entity);
        }
    }

    private static void animalDeathOnyxCreationRitual(Entity entity) {
        Level level = entity.getLevel();

        if (WorldActions.replaceNear(level, entity.getOnPos().above(), BlockInit.ONYX_BLOCK.get().defaultBlockState(), Blocks.STONE, 1)) {
            WorldActions.playSound(entity, SoundEvents.AMBIENT_CAVE);

            Entity lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
            lightningBolt.setPos(entity.getX(), entity.getY(), entity.getZ());
            level.addFreshEntity(lightningBolt);
        }
    }
}
