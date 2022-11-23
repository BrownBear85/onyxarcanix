package com.brownbear85.onyxarcanix.spell.spells;

import com.brownbear85.onyxarcanix.entity.SpellEntity;
import com.brownbear85.onyxarcanix.particle.MagicParticleOptions;
import com.mojang.math.Vector3f;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ZapShotSpell extends ProjectileSpell {
    public ZapShotSpell(String id) {
        super(id);
    }

    @Override
    public Vector3f getColor() {
        return new Vector3f(143/256F, 213/256F, 227/256F);
    }

    @Override
    public void onProjectileHit(Level level, Entity caster, SpellEntity entity, HitResult result) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(MagicParticleOptions.SPELL_EXPLOSION(getColor()), entity.getX(), entity.getY(), entity.getZ(), 45, 0, 0, 0, 0.5);
        }
        LightningBolt zap = EntityType.LIGHTNING_BOLT.create(level);
        zap.setPos(entity.getPosition(0));
        level.addFreshEntity(zap);
    }
}
