package com.brownbear85.onyxarcanix.spell.spells;

import com.brownbear85.onyxarcanix.particle.MagicParticleOptions;
import com.brownbear85.onyxarcanix.spell.Spell;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class RegrowthSpell extends Spell { // TODO make this spell a constant AOE in front of the player
    public RegrowthSpell(String id) {
        super(id);
    }

    @Override
    public Vector3f getColor() {
        return new Vector3f(39/256F, 196/256F, 66/256F);
    }

    @Override
    public boolean cast(Level level, Player caster) {
        HitResult result = Minecraft.getInstance().hitResult;
        if (result.getType() != HitResult.Type.BLOCK) {
            return false;
        }
        BlockPos centerPos = ((BlockHitResult) result).getBlockPos();
        if (level.getBlockState(centerPos).getBlock() instanceof BonemealableBlock) {
            castSound(level, caster);
            for (int i = 0; i < 3; i++) {
                level.playSound(caster, centerPos.getX(), centerPos.getY(), centerPos.getZ(), SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            }
            for (BlockPos pos : BlockPos.betweenClosed(centerPos.offset(-1, 0, -1), centerPos.offset(1, 1, 1))) {
                BoneMealItem.applyBonemeal(ItemStack.EMPTY, level, pos, caster);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(MagicParticleOptions.SMALL_DEFAULT(getColor()), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 8, 0.3, 0.3, 0.3, 0.1);
                }
            }
            return true;
        }
        return false;
    }
}
