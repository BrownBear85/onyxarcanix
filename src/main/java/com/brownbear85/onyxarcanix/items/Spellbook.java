package com.brownbear85.onyxarcanix.items;

import com.brownbear85.onyxarcanix.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Spellbook extends Item {
    public Spellbook(Properties pProperties) {
        super(pProperties);
    }

    public static ItemStack withSpell(String spell) {
        ItemStack stack = new ItemStack(ItemInit.SPELLBOOK.get());
        stack.getOrCreateTag().putString("spell", spell);
        return stack;
    }

    @Override
    public ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.hasTag()) {
            String spell = pStack.getTag().getString("spell");
            pTooltipComponents.add(Component.translatable("spell.onyxarcanix." + spell).withStyle(ChatFormatting.GRAY));
            pTooltipComponents.add(Component.translatable("spell_description.onyxarcanix." + spell).withStyle(ChatFormatting.DARK_PURPLE));
        }
    }
}
