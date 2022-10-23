package com.brownbear85.onyxarcanix.events;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blockentities.renderer.PedestalBlockEntityRenderer;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = OnyxArcanix.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

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
