package com.brownbear85.onyxarcanix.init;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OnyxArcanix.MODID);

    public static final RegistryObject<SimpleParticleType> ALTAR_PARTICLES = PARTICLE_TYPES.register("altar_particles", () -> new SimpleParticleType(true));


    public static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }
}
