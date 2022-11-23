package com.brownbear85.onyxarcanix.particle;

import com.brownbear85.onyxarcanix.blocks.entities.AltarBlockEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MagicParticle<T extends MagicParticleOptions> extends TextureSheetParticle {
    protected final int behavior;
    protected double destX, destY, destZ;

    protected MagicParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, MagicParticleOptions pOptions) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0.8F;
        this.hasPhysics = false;

        this.rCol = pOptions.getColor().x();
        this.gCol = pOptions.getColor().y();
        this.bCol = pOptions.getColor().z();

        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;

        this.lifetime = 80;
        this.quadSize = pOptions.scale;
        this.alpha = 0.9F;

        this.behavior = pOptions.behavior;

        double diffX = xo - Math.floor(xo);
        double diffZ = zo - Math.floor(zo);
        BlockPos destPos = new BlockPos(xo, yo, zo);
        switch (this.behavior) {
            case 0: // altar other
                this.lifetime = 40;
                this.alpha = 0.5F;
                break;
            case 1: // altar stream directions
                destPos = destPos.north(AltarBlockEntity.PEDESTAL_DISTANCE);
                break;
            case 2:
                destPos = destPos.south(AltarBlockEntity.PEDESTAL_DISTANCE);
                break;
            case 3:
                destPos = destPos.east(AltarBlockEntity.PEDESTAL_DISTANCE);
                break;
            case 4:
                destPos = destPos.west(AltarBlockEntity.PEDESTAL_DISTANCE);
                break;
            case 5: // spell particle
                this.lifetime = 30;
                this.hasPhysics = true;
                break;
            case 6:
                break;
            case 7:
                this.lifetime = 160;
                this.hasPhysics = true;
                break;
        }
        destX = destPos.getX() + 0.5;
        destY = destPos.getY() + 0.5;
        destZ = destPos.getZ() + 0.5;

        this.move((level.random.nextDouble() - 0.5) * 0.1, (level.random.nextDouble() - 0.5) * 0.1, (level.random.nextDouble() - 0.5) * 0.1);
    }

    @Override
    public void tick() {
        super.tick();
        if (behavior >= 1 && behavior <= 4) {
            streamfade();
            if (age <= 68) {
                this.move((xo - destX) / 100, (yo - destY) / 100, (zo - destZ) / 100);
            }
        } else {
            if (behavior == 5) {
                this.move(0.0, 0.05, 0.0);
                quadSize -= 0.01;
            }
            fadeout();
        }
    }

    public void streamfade() {
        this.alpha = Math.max(0, (0.8F - 0.1F * Math.max(0, age - lifetime + 10)));
    }

    public void fadeout() {
        this.alpha = Math.max(0, (0.5F - (1 / (float) this.lifetime) * age));
    }

    public static ParticleRenderType MAGIC_PARTICLE = new ParticleRenderType() {
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
            return "MAGIC_PARTICLE";
        }
    };

    @Override
    public ParticleRenderType getRenderType() {
        return MAGIC_PARTICLE;
    }

    public static class Provider implements ParticleProvider<MagicParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        @Nullable
        @Override
        public Particle createParticle(MagicParticleOptions pOptions, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            MagicParticle<MagicParticleOptions> magicParticle = new MagicParticle<>(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pOptions);
            magicParticle.pickSprite(this.sprites);
            return magicParticle;
        }
    }
}
