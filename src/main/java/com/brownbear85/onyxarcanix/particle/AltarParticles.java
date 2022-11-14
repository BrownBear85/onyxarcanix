package com.brownbear85.onyxarcanix.particle;

import com.brownbear85.onyxarcanix.blocks.entities.AltarBlockEntity;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import com.brownbear85.onyxarcanix.init.BlockInit;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AltarParticles extends TextureSheetParticle {
    double destX, destY, destZ;

    protected AltarParticles(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);

        this.friction = 0.8F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;

        if (xd == 0) {
            this.lifetime = 80;
            this.quadSize = 0.2F;
        } else {
            this.lifetime = 40;
            this.quadSize = 0.1F;
            this.alpha = 0.5F;
        }
        this.hasPhysics = false;

        double diffX = xo - Math.floor(xo);
        double diffZ = zo - Math.floor(zo);
        BlockPos destPos = new BlockPos(xo, yo, zo);
        if (diffX == 0.5) {
            if (diffZ > 0.5) {
                destPos = destPos.north(AltarBlockEntity.PEDESTAL_DISTANCE);
            } else {
                destPos = destPos.south(AltarBlockEntity.PEDESTAL_DISTANCE);
            }
        } else {
            if (diffX > 0.5) {
                destPos = destPos.west(AltarBlockEntity.PEDESTAL_DISTANCE);
            } else {
                destPos = destPos.east(AltarBlockEntity.PEDESTAL_DISTANCE);
            }
        }
        destX = destPos.getX() + 0.5;
        destY = destPos.getY() + 0.5;
        destZ = destPos.getZ() + 0.5;

        this.move((level.random.nextDouble() - 0.5) * 0.1, (level.random.nextDouble() - 0.5) * 0.1, (level.random.nextDouble() - 0.5) * 0.1);

        this.rCol = 0;
        this.gCol = 0;
        this.bCol = 0;
    }

    @Override
    public void tick() {
        super.tick();
        streamfade();
        if (xd == 0) {
            if (age <= 68) {
                this.move((xo - destX) / 100, (yo - destY) / 100, (zo - destZ) / 100);
            }
        } else {
            fadeout();
        }
    }

    public void streamfade() {
        this.alpha = Math.max(0, (0.8F - 0.1F * Math.max(0, age - lifetime + 10)));
    }

    public void fadeout() {
        this.alpha = Math.max(0, (0.5F - (1 / (float) this.lifetime) * age));
    }

    public static ParticleRenderType ALTAR_PARTICLE = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            RenderSystem.depthMask(false);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator pTesselator) {
            pTesselator.end();
        }

        @Override
        public String toString() {
            return "ALTAR_PARTICLE";
        }
    };

    @Override
    public ParticleRenderType getRenderType() {
        return ALTAR_PARTICLE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            AltarParticles altarparticles = new AltarParticles(level, x, y, z, dx, dy, dz);
            altarparticles.pickSprite(this.sprites);
            return altarparticles;
        }
    }
}
