package com.brownbear85.onyxarcanix.spell.spells;

import com.brownbear85.onyxarcanix.entity.SpellEntity;
import com.brownbear85.onyxarcanix.spell.Spell;
import com.mojang.math.Vector3f;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public abstract class ProjectileSpell extends Spell {
    public ProjectileSpell(String id) {
        super(id);
    }

    @Override
    public boolean cast(Level level, Player caster) {
        castSound(level, caster);
        Vector3f color = getColor();
        color.add((float) (level.random.nextFloat() * 0.1), (float) (level.random.nextFloat() * 0.1), (float) (level.random.nextFloat() * 0.1));
        SpellEntity.shootFromEntity(level, caster, color, id);
        return true;
    }

    public abstract void onProjectileHit(Level level, Entity caster, SpellEntity entity, HitResult result);
}
