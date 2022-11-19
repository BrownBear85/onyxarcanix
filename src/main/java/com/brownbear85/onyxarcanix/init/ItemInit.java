package com.brownbear85.onyxarcanix.init;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.items.OnyxApple;
import com.brownbear85.onyxarcanix.items.OnyxChisel;
import com.brownbear85.onyxarcanix.items.RitualKnife;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnyxArcanix.MODID);

    /* crafting items */

    public static final RegistryObject<Item> ONYX_IRON = ITEMS.register("onyx_iron",
            () -> new Item(properties()));
    public static final RegistryObject<Item> ONYX_IRON_ROD = ITEMS.register("onyx_iron_rod",
            () -> new Item(properties()));

    /* functional items */

    public static final RegistryObject<Item> ONYX_CHISEL = ITEMS.register("onyx_chisel",
            () -> new OnyxChisel(properties().defaultDurability(192)));

    public static final RegistryObject<Item> ONYX_APPLE = ITEMS.register("onyx_apple",
            () -> new OnyxApple(properties().food(Foods.ONYX_APPLE)));

    public static final RegistryObject<Item> WAND = ITEMS.register("wand",
            () -> new OnyxApple(properties()));

    public static final RegistryObject<SwordItem> RITUAL_KNIFE = ITEMS.register("ritual_knife",
            () -> new RitualKnife(Tiers.TIER_RITUAL_KNIFE,0, -2.6F, properties().defaultDurability(128)));


    /* registry */

    public static Item.Properties properties() {
        return new Item.Properties().tab(OnyxArcanix.TAB);
    }

    public static class Foods {
        public static final FoodProperties ONYX_APPLE = new FoodProperties.Builder().nutrition(0).saturationMod(0.0F)
                .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 6000, 0), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.WITHER, 6000, 4), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 6000, 4), 1.0F)
                .alwaysEat().build();
    }

    public static class Tiers {
        public static final Tier TIER_RITUAL_KNIFE = new ForgeTier(0, 100, 6.0F, 3.0F, 0, null, () -> Ingredient.EMPTY);
    }
}
