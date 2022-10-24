package com.brownbear85.onyxarcanix.blocks;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.utility.ClientAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> components, TooltipFlag flag) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.chiselableTooltip(components));
    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        super.tick(state, serverLevel, blockPos, randomSource);
        serverLevel.playSound(null, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, SoundEvents.STONE_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
        serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 60, 0.3, 0.3, 0.3, 0);
    }

    public static Runes getRuneFromString(String str) {
        for (Runes rune : Runes.values()) {
            if (rune.toString().toLowerCase().equals(str)) {
                return rune;
            }
        }
        return Runes.BLANK;
    }

    public static final String[][] RUNE_NAME_TABLE = {
            {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"},
            {"air", "b", "c", "diamond", "earth", "the forest", "gold", "h", "iron", "golem", "llama", "the lake", "the mountains", "n", "obsidian", "the plains", "dolphin", "r", "silver", "tadpole", "u", "valley", "water", "axolotl", "magic", "fire"}
    };

    public static String getRuneName(String rune) {
        for (int i = 0; i < RUNE_NAME_TABLE[0].length; i++) {
            if (RUNE_NAME_TABLE[0][i].toLowerCase().equals(rune)) {
                return RUNE_NAME_TABLE[1][i];
            }
        }
        return "";
    }

    public static String getRuneName(Runes rune) {
        return getRuneName(rune.toString());
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
