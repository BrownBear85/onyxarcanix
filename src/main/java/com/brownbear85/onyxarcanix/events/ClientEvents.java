package com.brownbear85.onyxarcanix.events;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blocks.ItemHolderBlock;
import com.brownbear85.onyxarcanix.blocks.entities.renderer.ItemHolderBlockEntityRenderer;
import com.brownbear85.onyxarcanix.client.TextHudOverlay;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;

import com.brownbear85.onyxarcanix.init.BlockInit;
import com.brownbear85.onyxarcanix.init.ItemInit;
import com.brownbear85.onyxarcanix.init.ParticleInit;
import com.brownbear85.onyxarcanix.networking.ModNetworking;
import com.brownbear85.onyxarcanix.networking.packets.ChiselCycleRuneC2SPacket;
import com.brownbear85.onyxarcanix.client.KeyBindings;
import com.brownbear85.onyxarcanix.particle.MagicParticleOptions;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = OnyxArcanix.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBindings.CYCLE_RUNE.consumeClick()) {
                Player player = Minecraft.getInstance().player;
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (stack.is(ItemInit.ONYX_CHISEL.get())) {
                    ModNetworking.sendToServer(new ChiselCycleRuneC2SPacket(stack));
                }
            }
            if (KeyBindings.TEST.consumeClick()) {
                Player player = Minecraft.getInstance().player;
                Level level = player.getLevel();
                if (level instanceof ClientLevel clientLevel) {
                    for (int i = 0; i < 30; i++) {
                        clientLevel.addParticle(MagicParticleOptions.ALTAR_OTHER, player.getX() + level.random.nextDouble() - 0.5,
                    player.getY() + level.random.nextDouble() - 0.5,
                    player.getZ() + level.random.nextDouble() - 0.5,
                        0, 0, 0);
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = OnyxArcanix.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBindings.CYCLE_RUNE);
            event.register(KeyBindings.TEST);
        }

        @SubscribeEvent
        public static void registerRenders(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityInit.PEDESTAL_BLOCK_ENTITY.get(),
                    ItemHolderBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityInit.ALTAR_BLOCK_ENTITY.get(),
                    ItemHolderBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("chisel", TextHudOverlay.HUD_CHISEL);
        }
    }
}
