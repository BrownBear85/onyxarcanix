package com.brownbear85.onyxarcanix.utility;

import com.brownbear85.onyxarcanix.blocks.Chiselable;
import com.brownbear85.onyxarcanix.items.OnyxChisel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ClientAccess {
    public static void chiselableTooltip(List<Component> components) {
        if (!Screen.hasShiftDown()) {
            components.add(Component.translatable("runed_block.onyxarcanix.shift"));
        } else {
            components.add(Component.translatable("runed_block.onyxarcanix.description"));
        }
    }

    public static void chiselTooltip(List<Component> components, ItemStack stack) {
        if (stack.getOrCreateTag().getString("rune").equals("")) {
            stack.getOrCreateTag().putString("rune", OnyxChisel.DEFAULT_RUNE);
        }
        components.add(Component.translatable("chisel.onyxarcanix.currentRune", "\u00A77" + Chiselable.getRuneName(stack.getOrCreateTag().getString("rune"))));
        components.add(Component.translatable("chisel.onyxarcanix.description"));
    }
}
