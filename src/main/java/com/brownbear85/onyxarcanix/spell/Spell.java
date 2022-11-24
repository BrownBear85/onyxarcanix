package com.brownbear85.onyxarcanix.spell;

import com.brownbear85.onyxarcanix.init.SoundInit;
import com.brownbear85.onyxarcanix.init.SpellInit;
import com.mojang.math.Vector3f;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class Spell {
    public Spell(String id) {
        SpellInit.REGISTRY.put(id, this);
        this.id = id;
    }

    public void castSound(Level level, Entity caster) {
        level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundInit.CAST_SPELL.get(), SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 1.0F + 0.8F));
    }

    protected final String id;

    public abstract Vector3f getColor();

    public String getId() {
        return id;
    }

    public abstract boolean cast(Level level, Player caster);
}
