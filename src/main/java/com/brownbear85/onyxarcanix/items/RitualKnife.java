package com.brownbear85.onyxarcanix.items;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.init.ItemInit;
import com.brownbear85.onyxarcanix.utility.EntityRituals;
import com.brownbear85.onyxarcanix.utility.WorldActions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class RitualKnife extends SwordItem {
    public RitualKnife(Tier tier, int damage, float speed, Item.Properties properties) {
        super(tier, damage, speed, properties);
    }

    private static void damageItem(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        WorldActions.playSound(player, SoundEvents.FIRE_EXTINGUISH);
        WorldActions.playSound(player, SoundEvents.PLAYER_BURP);

        player.swing(hand);
        player.hurt(OnyxArcanix.DAMAGE_SELF_SACRIFICE, 3.0F);
        player.getCooldowns().addCooldown(ItemInit.RITUAL_KNIFE.get(), 40);

        ItemStack stack = player.getItemInHand(hand);
        damageItem(player, player.getItemInHand(hand));
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!entity.getType().equals(EntityType.ARMOR_STAND)) {
            Level level = entity.getLevel();

            if (entity.invulnerableTime == 0) {
                entity.hurt(OnyxArcanix.playerSacrifice(player), 3.0F);
                damageItem(player, stack);

                WorldActions.playSound(entity, SoundEvents.FIRE_EXTINGUISH);
                WorldActions.playSound(entity, SoundEvents.PLAYER_BURP);
            }
            if (entity instanceof LivingEntity livingEntity) {
                float health = livingEntity.getHealth();
                if (level.isClientSide() && health - 3.0F <= 0.0F || !level.isClientSide() && health <= 0.0F) {
                    EntityRituals.doEntityDeathRitual(livingEntity);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Mth.hsvToRgb(0.1325F, 0.704F, 0.98F);
    }
}
