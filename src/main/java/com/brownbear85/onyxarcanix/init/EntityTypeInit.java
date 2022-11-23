package com.brownbear85.onyxarcanix.init;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.entity.SpellEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OnyxArcanix.MODID);

    public static final RegistryObject<EntityType<SpellEntity>> SPELL =
            ENTITY_TYPES.register("spell",
                    () -> EntityType.Builder.of(SpellEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .build(new ResourceLocation(OnyxArcanix.MODID, "spell").toString()));


    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
