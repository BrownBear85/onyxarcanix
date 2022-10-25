package com.brownbear85.onyxarcanix.client;

import com.brownbear85.onyxarcanix.blocks.Chiselable;
import com.brownbear85.onyxarcanix.init.ItemInit;
import com.brownbear85.onyxarcanix.util.Ray;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class TextHudOverlay {
    private static final Font font = Minecraft.getInstance().font;

    public static final IGuiOverlay HUD_CHISEL = ((gui, poseStack, partialTick, width, height) -> {
        Player player = gui.getMinecraft().player;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.is(ItemInit.ONYX_CHISEL.get())) {
            font.drawShadow(new PoseStack(),
                    Component.translatable("gui.onyxarcanix.currentRune").append(
                    Component.translatable("rune.onyxarcanix." + stack.getOrCreateTag().getString("rune"))).withStyle(ChatFormatting.GRAY),
                    width * 0.5F + 6.5F, height * 0.5F, 0);
        }

        Ray ray = new Ray(player);
        BlockState state = ray.cast(player.getReachDistance(), 0.5).getState();
        if (state.getBlock() instanceof Chiselable) {
            Chiselable.Runes rune = state.getValue(Chiselable.RUNE);
            if (rune == Chiselable.Runes.BLANK) {
                font.drawShadow(new PoseStack(),
                        Component.translatable("rune.onyxarcanix.blank").withStyle(ChatFormatting.DARK_GRAY),
                 width * 0.5F + 6.5F, height * 0.5F - 10.0F, 0);
            } else {
                font.drawShadow(new PoseStack(),
                        Component.translatable("gui.onyxarcanix.currentRune").append(
                        Component.translatable("rune.onyxarcanix." + rune)).withStyle(ChatFormatting.DARK_GRAY),
                        width * 0.5F + 6.5F, height * 0.5F - 10.0F, 0);
            }

        }
    });


}
