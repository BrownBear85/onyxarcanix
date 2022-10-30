package com.brownbear85.onyxarcanix;

import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import com.brownbear85.onyxarcanix.init.BlockInit;
import com.brownbear85.onyxarcanix.init.ItemInit;
import com.brownbear85.onyxarcanix.networking.ModNetworking;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

@Mod(OnyxArcanix.MODID)
public class OnyxArcanix {
    public static final String MODID = "onyxarcanix";

    public static final DamageSource DAMAGE_ONYX_APPLE = (new DamageSource("onyxApple")).bypassArmor();
    public static final DamageSource DAMAGE_SELF_SACRIFICE = (new DamageSource("sacrifice")).bypassArmor();
    public static DamageSource playerSacrifice(Player player) {
        return new EntityDamageSource("playerSacrifice", player);
    }

    public OnyxArcanix() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemInit.RITUAL_KNIFE.get().getDefaultInstance();
        }
    };
}