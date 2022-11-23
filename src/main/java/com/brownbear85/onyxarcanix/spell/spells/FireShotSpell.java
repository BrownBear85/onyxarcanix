package com.brownbear85.onyxarcanix.spell.spells;

import com.brownbear85.onyxarcanix.entity.SpellEntity;
import com.brownbear85.onyxarcanix.particle.MagicParticleOptions;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class FireShotSpell extends ProjectileSpell {
    public FireShotSpell(String id) {
        super(id);
    }

    @Override
    public Vector3f getColor() {
        return new Vector3f(230/256F, 43/256F, 18/256F);
    }

    @Override
    public void onProjectileHit(Level level, Entity caster, SpellEntity entity, HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity victim = ((EntityHitResult) result).getEntity();
            victim.hurt(DamageSource.MAGIC, 3);
            victim.setSecondsOnFire(3);
        }
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos replacePos = new BlockPos(entity.getPosition(0).add(entity.getDeltaMovement().multiply(-1, -1, -1)));
            BlockState replaceState = level.getBlockState(replacePos);
            if (replaceState.getMaterial().isReplaceable() && level.getBlockState(replacePos.below()).getMaterial().isSolid()) {
                level.setBlockAndUpdate(replacePos, Blocks.FIRE.defaultBlockState());
                level.updateNeighborsAt(replacePos, Blocks.FIRE);
            }
        }
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(MagicParticleOptions.SMALL_DEFAULT(getColor()), entity.getX(), entity.getY(), entity.getZ(), 20, 0, 0, 0, 0.25);
        }
    }
}
