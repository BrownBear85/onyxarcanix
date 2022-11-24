package com.brownbear85.onyxarcanix;

import com.brownbear85.onyxarcanix.init.*;
import com.brownbear85.onyxarcanix.init.RecipeInit;
import com.brownbear85.onyxarcanix.items.Spellbook;
import com.brownbear85.onyxarcanix.init.SpellInit;
import net.minecraft.core.NonNullList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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
        RecipeInit.register(bus);
        ParticleInit.register(bus);
        SoundInit.register(bus);
        EntityTypeInit.register(bus);

        SpellInit.registerSpells();
    }

    public static final CreativeModeTab MAIN_TAB = new CreativeModeTab(MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemInit.RITUAL_KNIFE.get().getDefaultInstance();
        }
    };

    public static final CreativeModeTab SPELL_TAB = new CreativeModeTab("onyxarcanix_spells") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemInit.SPELLBOOK.get().getDefaultInstance();
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> pItems) {
            pItems.add(ItemInit.EMPTY_SPELLBOOK.get().getDefaultInstance());
            pItems.add(Spellbook.withSpell("zap_shot"));
            pItems.add(Spellbook.withSpell("fire_shot"));
            pItems.add(Spellbook.withSpell("regrowth"));
        }
    };
}