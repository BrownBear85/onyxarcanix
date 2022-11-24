package com.brownbear85.onyxarcanix.client;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.init.ItemInit;
import com.brownbear85.onyxarcanix.init.SpellInit;
import com.brownbear85.onyxarcanix.items.Wand;
import com.brownbear85.onyxarcanix.spell.Spell;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SpellHudOverlay {
    private static final ResourceLocation SPELL_BORDER = new ResourceLocation(OnyxArcanix.MODID, "textures/gui/spell_hud/spell_hud.png");
    private static final ResourceLocation SPELL_BACKGROUND = new ResourceLocation(OnyxArcanix.MODID, "textures/gui/spell_hud/spell_background.png");
    private static final ResourceLocation NO_SPELL = new ResourceLocation(OnyxArcanix.MODID, "textures/gui/spell_hud/spells/no_spell.png");

    private static final Font font = Minecraft.getInstance().font;

    public static final IGuiOverlay HUD_SPELL = ((gui, poseStack, partialTick, width, height) -> {
        ItemStack stack = Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.is(ItemInit.WAND.get())) {
            int x = 25;
            int y = height - 75;

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, SPELL_BORDER);
            GuiComponent.blit(poseStack, x, y, 0, 0, 48, 48, 48, 48);

            Wand.addWandNBT(stack);

            ListTag spells = stack.getTag().getList("spells", 8);
            if (spells.size() > 0) {
                Spell spell = SpellInit.get(spells.get(stack.getTag().getInt("selected")).getAsString());
                if (spell == null) {
                    renderNoSpell(poseStack, x, y);
                    return;
                }
                Vector3f color = spell.getColor();
                color.lerp(new Vector3f(0.5F, 0.5F, 0.5F), 0.5F);

                font.drawShadow(new PoseStack(),
                        Component.translatable("spell.onyxarcanix." + spell.getId()).withStyle(ChatFormatting.WHITE),
                        x, y + 55, 0);

                RenderSystem.setShaderColor(color.x(), color.y(), color.z(), 1.0F);
                RenderSystem.setShaderTexture(0, SPELL_BACKGROUND);
                GuiComponent.blit(poseStack, x, y, 0, 0, 48, 48, 48, 48);

                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, new ResourceLocation(OnyxArcanix.MODID, "textures/gui/spell_hud/spells/" + spell.getId() + ".png"));
                GuiComponent.blit(poseStack, x + 8, y + 8, 0, 0, 32, 32, 32, 32);
            } else {
                renderNoSpell(poseStack, x, y);
            }
        }
    });

    public static void renderNoSpell(PoseStack poseStack, int x, int y) {
        font.drawShadow(new PoseStack(),
                Component.translatable("spell.onyxarcanix.no_spell").withStyle(ChatFormatting.DARK_RED),
                x, y + 55, 0);

        RenderSystem.setShaderColor(0.3F, 0.3F, 0.3F, 1.0F);
        RenderSystem.setShaderTexture(0, SPELL_BACKGROUND);
        GuiComponent.blit(poseStack, x, y, 0, 0, 48, 48, 48, 48);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, NO_SPELL);
        GuiComponent.blit(poseStack, x + 8, y + 8, 0, 0, 32, 32, 32, 32);
    }
}
