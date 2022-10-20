package com.brownbear85.onyxarcanix.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class Chiselable extends Block {
    public static final EnumProperty<Runes> RUNE = EnumProperty.create("rune", Runes.class);
    public static final IntegerProperty CARVING_STATE = IntegerProperty.create("carving_state", 0, 5);

    public Chiselable(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RUNE);
        builder.add(CARVING_STATE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(CARVING_STATE, 0);
    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        super.tick(state, serverLevel, blockPos, randomSource);
        serverLevel.playSound(null, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, SoundEvents.STONE_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
        serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 60, 0.3, 0.3, 0.3, 0);
    }

    public enum Runes implements StringRepresentable {
        BLANK("blank"), A("a"), B("b"), C("c"), D("d"), E("e"),
        F("f"), G("g"), H("h"), I("i"), J("j"), K("k"), L("l"),
        M("m"), N("n"), O("o"), P("p"), Q("q"), R("r"), S("s"),
        T("t"), U("u"), V("v"), W("w"), X("x"), Y("y"), Z("z");

        private final String name;

        Runes(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
