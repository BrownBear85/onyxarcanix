package com.brownbear85.onyxarcanix.items;

import com.brownbear85.onyxarcanix.init.SpellInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wand extends Item {
    public static final int SPELL_SLOTS = 4;

    public Wand(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        List<Tag> list = stack.getTag().getList("spells", 8);
        String spell = list.get(stack.getTag().getInt("selected")).getAsString();
        if (SpellInit.castSpell(pLevel, pPlayer, spell)) {
            pPlayer.swing(pUsedHand);
            return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        addWandNBT(stack);
        return stack;
    }

    public static void addWandNBT(ItemStack stack) {
        stack.getOrCreateTag();
        if (!stack.getTag().contains("spells")) {
            stack.getTag().put("spells", new ListTag());
        }
        if (!stack.getTag().contains("selected")) {
            stack.getTag().putInt("selected", 0);
        }
    }

    public static ItemStack addSpell(ItemStack stack, String spell) {
        addWandNBT(stack);
        ListTag list = stack.getTag().getList("spells", 8);
        if (list.size() < SPELL_SLOTS && !list.contains(spell)) {
            list.add(StringTag.valueOf(spell));
        }
        return stack;
    }

    public static ItemStack removeSpell(ItemStack stack, String spell) {
        addWandNBT(stack);
        ListTag list = stack.getTag().getList("spells", 8);
        if (list.contains(spell)) {
            list.remove(StringTag.valueOf(spell));
        }
        return stack;
    }

    public static ItemStack cycleSelected(ItemStack stack, int amount) {
        addWandNBT(stack);
        int spellsSize = stack.getTag().getList("spells", 8).size();
        int selected = stack.getTag().getInt("selected") + amount;
        if (selected > spellsSize - 1) {
            selected -= spellsSize;
        } else if (selected < 0) {
            selected = spellsSize + selected;
        }
        stack.getTag().putInt("selected", selected);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        addWandNBT(pStack);
        ListTag list = pStack.getTag().getList("spells", 8);
        if (!list.isEmpty()) {
            for (Tag str : list) {
                String spell = str.getAsString();
                pTooltipComponents.add(Component.literal("- ").withStyle(ChatFormatting.DARK_GRAY).append(Component.translatable("spell.onyxarcanix." + spell).withStyle(ChatFormatting.DARK_PURPLE)));
                if (Screen.hasShiftDown()) {
                    pTooltipComponents.add(Component.literal("  ").append(Component.translatable("spell_description.onyxarcanix." + spell).withStyle(ChatFormatting.DARK_GRAY)));
                }
            }
            if (!Screen.hasShiftDown()) {
                pTooltipComponents.add(Component.translatable("item.onyxarcanix.wand.shift").withStyle(ChatFormatting.DARK_GRAY));
            }
        } else {
            pTooltipComponents.add(Component.translatable("item.onyxarcanix.wand.no_spells").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
