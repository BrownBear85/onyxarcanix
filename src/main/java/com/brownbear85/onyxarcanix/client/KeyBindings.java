package com.brownbear85.onyxarcanix.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY_ONYXARCANIX = "key.category.onyxarcanix.category";

    public static final String KEY_CYCLE_RUNE = "key.onyxarcanix.cycle_rune";
    public static final String KEY_TEST = "key.onyxarcanix.test";
    public static final String KEY_SWITCH_SPELL_LEFT = "key.onyxarcanix.switch_spell_left";
    public static final String KEY_SWITCH_SPELL_RIGHT = "key.onyxarcanix.switch_spell_right";

    public static final KeyMapping CYCLE_RUNE = new KeyMapping(KEY_CYCLE_RUNE, KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY_ONYXARCANIX);
    public static final KeyMapping TEST = new KeyMapping(KEY_TEST, KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_COMMA, KEY_CATEGORY_ONYXARCANIX);
    public static final KeyMapping SWITCH_SPELL_LEFT = new KeyMapping(KEY_SWITCH_SPELL_LEFT, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_ONYXARCANIX);
    public static final KeyMapping SWITCH_SPELL_RIGHT = new KeyMapping(KEY_SWITCH_SPELL_RIGHT, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY_ONYXARCANIX);
}
