package com.brownbear85.onyxarcanix.utility;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClientAccess {
    public static void chiselableTooltip(List<Component> components) {
        if (!Screen.hasShiftDown()) {
            components.add(Component.translatable("runed_block.onyxarcanix.shift"));
        } else {
            components.add(Component.translatable("runed_block.onyxarcanix.description"));
        }
    }
}
