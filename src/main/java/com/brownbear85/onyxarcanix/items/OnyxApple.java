package com.brownbear85.onyxarcanix.items;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OnyxApple extends Item {
    public OnyxApple(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity player) {
        AttributeInstance maxHealth = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
        if (maxHealth.getValue() < 30.0f) maxHealth.setBaseValue(player.getAttributeValue(Attributes.MAX_HEALTH) + 1.0);
        else player.hurt(OnyxArcanix.DAMAGE_ONYX_APPLE, 100.0f);
        Entity lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
        lightningBolt.setPos(player.getX(), player.getY(), player.getZ());
        level.addFreshEntity(lightningBolt);
        return super.finishUsingItem(stack, level, player);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 128;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.AMBIENT_CAVE;
    }
}
