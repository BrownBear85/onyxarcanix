package com.brownbear85.onyxarcanix.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY_ONYXARCANIX = "key.category.onyxarcanix.category";
    public static final String KEY_CYCLE_RUNE = "key.onyxarcanix.cycle_rune";

    public static final KeyMapping CYCLE_RUNE = new KeyMapping(KEY_CYCLE_RUNE, KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY_ONYXARCANIX);
}
