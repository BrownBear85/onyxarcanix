package com.brownbear85.onyxarcanix.events;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blocks.entities.renderer.PedestalBlockEntityRenderer;
import com.brownbear85.onyxarcanix.blocks.Chiselable;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;

import com.brownbear85.onyxarcanix.init.ItemInit;
import com.brownbear85.onyxarcanix.init.KeyBindings;
import com.brownbear85.onyxarcanix.items.OnyxChisel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = OnyxArcanix.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBindings.CYCLE_RUNE.consumeClick()) {
                Player player = Minecraft.getInstance().player;
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (stack.is(ItemInit.ONYX_CHISEL.get())) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, OnyxChisel.cycleRune(stack, Chiselable.RUNE_NAME_TABLE[0]));
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = OnyxArcanix.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenders(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityInit.PEDESTAL.get(),
                    PedestalBlockEntityRenderer::new);
        }
    }
}
