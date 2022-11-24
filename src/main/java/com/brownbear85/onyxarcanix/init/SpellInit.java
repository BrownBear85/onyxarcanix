package com.brownbear85.onyxarcanix.init;

import com.brownbear85.onyxarcanix.spell.Spell;
import com.brownbear85.onyxarcanix.spell.spells.FireShotSpell;
import com.brownbear85.onyxarcanix.spell.spells.RegrowthSpell;
import com.brownbear85.onyxarcanix.spell.spells.ZapShotSpell;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SpellInit {
    public static final Object2ObjectArrayMap<String, Spell> REGISTRY = new Object2ObjectArrayMap<>();

    public static void registerSpells() {
        final Spell ZAP_SHOT = new ZapShotSpell("zap_shot");
        final Spell FIRE_SHOT = new FireShotSpell("fire_shot");
        final Spell REGROWTH = new RegrowthSpell("regrowth");
    }

    public static Spell get(String key) {
        return REGISTRY.get(key);
    }

    public static boolean castSpell(Level level, Player caster, String spell) {
        Spell spellObj = REGISTRY.get(spell);
        if (spellObj != null) {
            return spellObj.cast(level, caster);
        }
        return false;
    }
}
