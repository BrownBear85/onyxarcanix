package com.brownbear85.onyxarcanix.events;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.capabilities.PlayerRunes;
import com.brownbear85.onyxarcanix.capabilities.PlayerRunesProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// go to the Event class, then press Ctrl+H to see all events
import net.minecraftforge.eventbus.api.Event;

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
}
