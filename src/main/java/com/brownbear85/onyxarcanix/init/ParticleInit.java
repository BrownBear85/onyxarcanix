package com.brownbear85.onyxarcanix.init;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.particle.MagicParticleOptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OnyxArcanix.MODID);

    public static final RegistryObject<SimpleParticleType> ALTAR_PARTICLES = PARTICLE_TYPES.register("altar_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<ParticleType<MagicParticleOptions>> MAGIC_PARTICLE = PARTICLE_TYPES.register("magic_particle", () -> new ParticleType<>(true, MagicParticleOptions.DESERIALIZER) {
        @Override
        public Codec<MagicParticleOptions> codec() {
            return MagicParticleOptions.CODEC;
        }
    });


    public static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }


}
