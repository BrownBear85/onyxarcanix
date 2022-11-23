package com.brownbear85.onyxarcanix.events;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blocks.ItemHolderBlock;
import com.brownbear85.onyxarcanix.capabilities.PlayerRunes;
import com.brownbear85.onyxarcanix.capabilities.PlayerRunesProvider;
import com.brownbear85.onyxarcanix.entity.client.SpellRenderer;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import com.brownbear85.onyxarcanix.init.BlockInit;
import com.brownbear85.onyxarcanix.init.EntityTypeInit;
import com.brownbear85.onyxarcanix.init.ParticleInit;
import com.brownbear85.onyxarcanix.networking.ModNetworking;
import com.brownbear85.onyxarcanix.particle.MagicParticle;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = OnyxArcanix.MODID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(PlayerRunes.PLAYER_RUNES).isPresent()) {
                    event.addCapability(new ResourceLocation(OnyxArcanix.MODID, "properties"), new PlayerRunesProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerRunes.PLAYER_RUNES).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerRunes.PLAYER_RUNES).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }

    @Mod.EventBusSubscriber(modid = OnyxArcanix.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModNetworking.register();

            EntityRenderers.register(EntityTypeInit.SPELL.get(), SpellRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLCommonSetupEvent event) {
            ((ItemHolderBlock) BlockInit.STONE_PEDESTAL.get()).type = BlockEntityInit.PEDESTAL_BLOCK_ENTITY.get();
            ((ItemHolderBlock) BlockInit.ALTAR.get()).type = BlockEntityInit.ALTAR_BLOCK_ENTITY.get();
            ((ItemHolderBlock) BlockInit.ONYX_ALTAR.get()).type = BlockEntityInit.ALTAR_BLOCK_ENTITY.get();
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.register(ParticleInit.MAGIC_PARTICLE.get(), MagicParticle.Provider::new);
        }
    }
}
