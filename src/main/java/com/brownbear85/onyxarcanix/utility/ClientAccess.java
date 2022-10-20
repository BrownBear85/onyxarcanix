package com.brownbear85.onyxarcanix.utility;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClientAccess {
    public static void ritualKnifeTooltip(List<Component> components) {
        if (Screen.hasShiftDown()) {
            components.add(Component.literal("press shift for more info"));
        } else {
            components.add(Component.literal("test message"));
        }
    }
}
