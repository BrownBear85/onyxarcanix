package com.brownbear85.onyxarcanix.particle;

import com.brownbear85.onyxarcanix.init.ParticleInit;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

import java.util.Locale;

public class MagicParticleOptions implements ParticleOptions {
    public static final Vector3f BLACK_COLOR = new Vector3f(0.0F, 0.0F, 0.0F);

    public static MagicParticleOptions ALTAR_STREAM(Direction direction) {
        int behavior = 1;
        switch (direction) {
            case SOUTH -> behavior = 2;
            case EAST -> behavior = 3;
            case WEST -> behavior = 4;
        }
        return new MagicParticleOptions(BLACK_COLOR, 0.2F, behavior);
    }

    public static final MagicParticleOptions ALTAR_OTHER = new MagicParticleOptions(BLACK_COLOR, 0.1F, 0);



    public static final float MAX_SCALE = 4.0F;
    public static final float MIN_SCALE = 0.01F;

    protected final Vector3f color;
    protected final float scale;
    protected final int behavior;

    public MagicParticleOptions(Vector3f color, float scale, int behavior) {
        this.color = color;
        this.scale = Mth.clamp(scale, 0.01F, 4.0F);
        this.behavior = behavior;
    }

    public static final Codec<MagicParticleOptions> CODEC = RecordCodecBuilder.create((codecBuilder) -> {
        return codecBuilder.group(Vector3f.CODEC.fieldOf("color").forGetter((particle) -> {
            return particle.color;
        }), Codec.FLOAT.fieldOf("scale").forGetter((particle) -> {
            return particle.scale;
        }), Codec.INT.fieldOf("behavior").forGetter((particle) -> {
            return particle.behavior;
        })).apply(codecBuilder, MagicParticleOptions::new);
    });

    @Override
    public ParticleType<?> getType() {
        return ParticleInit.MAGIC_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(color.x());
        pBuffer.writeFloat(color.y());
        pBuffer.writeFloat(color.z());
        pBuffer.writeFloat(scale);
        pBuffer.writeInt(behavior);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", this.getType(), color.x(), color.y(), color.z(), scale, behavior);
    }

    public static final ParticleOptions.Deserializer<MagicParticleOptions> DESERIALIZER = new Deserializer<MagicParticleOptions>() {
        @Override
        public MagicParticleOptions fromCommand(ParticleType<MagicParticleOptions> pParticleType, StringReader pReader) throws CommandSyntaxException {
            Vector3f color = DustParticleOptions.readVector3f(pReader);
            pReader.expect(' ');
            float scale = pReader.readFloat();
            pReader.expect(' ');
            int behavior = pReader.readInt();
            return new MagicParticleOptions(color, scale, behavior);
        }

        @Override
        public MagicParticleOptions fromNetwork(ParticleType<MagicParticleOptions> pParticleType, FriendlyByteBuf pBuffer) {
            return new MagicParticleOptions(DustParticleOptions.readVector3f(pBuffer), pBuffer.readFloat(), pBuffer.readInt());
        }
    };

    public Vector3f getColor() {
        return this.color;
    }

    public float getScale() {
        return this.scale;
    }
}
