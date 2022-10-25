package com.brownbear85.onyxarcanix.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Ray {
    private Level level;
    private Vec3 pos;
    private Vec3 angle;
    private Vec3 oldPos;

    public Ray(Level level, Vec3 pos, Vec3 angle) {
        this.level = level;
        this.pos = pos;
        this.angle = angle;
    }

    public Ray(Entity entity) {
        this.level = entity.getLevel();
        this.pos = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
        this.angle = entity.getLookAngle();
    }

    public Vec3 getPos() {
        return this.pos;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(this.pos);
    }

    public BlockState getBlockState() {
        return level.getBlockState(this.getBlockPos());
    }

    public RayCastResult cast(double distance, double step) {
        for (double d = 0.0D; d < distance; d += step) {
            this.oldPos = pos;
            this.pos = new Vec3(this.pos.x + this.angle.x * step, this.pos.y + this.angle.y * step, this.pos.z + this.angle.z * step);

            if (!this.getBlockState().isAir()) {
                this.pos = this.oldPos;
                double preciseStep = step * 0.1;
                for (double e = 0.0D; e < distance; e += preciseStep) {
                    this.pos = new Vec3(this.pos.x + this.angle.x * preciseStep, this.pos.y + this.angle.y * preciseStep, this.pos.z + this.angle.z * preciseStep);

                    if (!this.getBlockState().isAir()) {
                        return new RayCastResult(true, this.pos, this.getBlockState());
                    }
                }
            }
        }
        return new RayCastResult(false, this.pos, this.getBlockState());
    }

    public class RayCastResult {
        private boolean hit;
        private Vec3 pos;
        private BlockState state;

        private RayCastResult(boolean hit, Vec3 pos, BlockState state) {
            this.hit = hit;
            this.pos = pos;
            this.state = state;
        }

        public boolean hit() {
            return this.hit;
        }

        public Vec3 getEndPos() {
            return this.pos;
        }

        public BlockPos getEndBlockPos() {
            return new BlockPos(this.pos);
        }

        public BlockState getState() {
            return this.state;
        }
    }
}
