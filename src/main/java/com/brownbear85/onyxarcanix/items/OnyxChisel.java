package com.brownbear85.onyxarcanix.items;

import com.brownbear85.onyxarcanix.blocks.Chiselable;
import com.brownbear85.onyxarcanix.init.BlockInit;
import com.brownbear85.onyxarcanix.capabilities.PlayerRunes;
import com.brownbear85.onyxarcanix.util.KeyBindings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class OnyxChisel extends Item {
    public OnyxChisel(Item.Properties properties) {
        super(properties);
    }

    public static final String DEFAULT_RUNE = "b";

    public static ItemStack cycleRune(ItemStack item, Player player) {
        CompoundTag tag = item.getOrCreateTag();
        String currentRune = tag.getString("rune");
        String[] arr = player.getCapability(PlayerRunes.PLAYER_RUNES).resolve().get().getRunes();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(currentRune)) {
                tag.putString("rune", arr[i + 1 == arr.length ? 0 : i + 1]);
            }
        }
        return item;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (!state.is(BlockInit.RUNED_STONE_BRICKS.get())) {return InteractionResult.FAIL;}
        if (!Objects.equals(state.getValue(Chiselable.RUNE).getSerializedName(), "blank")) {return InteractionResult.FAIL;}

        InteractionHand hand = context.getHand();
        if (hand == InteractionHand.OFF_HAND) {return InteractionResult.FAIL;}

        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        Direction direction = context.getClickedFace() != Direction.UP && context.getClickedFace() != Direction.DOWN ? context.getClickedFace() : context.getHorizontalDirection().getOpposite();

        String str = player.getItemInHand(hand).getOrCreateTag().getString("rune");
        if (str.equals("")) {
            stack.getOrCreateTag().putString("rune", DEFAULT_RUNE);
        }
        Chiselable.Runes rune = Chiselable.getRuneFromString(str);

        switch (state.getValue(Chiselable.CARVING_STATE)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 30, 0.3, 0.3, 0.3, 0);
                }
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.STONE_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.getCooldowns().addCooldown(this, 10);
                level.scheduleTick(pos, state.getBlock(), 5);
                stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, state.setValue(Chiselable.CARVING_STATE, state.getValue(Chiselable.CARVING_STATE) + 1), 3);
                return InteractionResult.SUCCESS;

            case 5:
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + direction.getStepX() * 0.6 + 0.5, pos.getY() + direction.getStepY() * 0.6 + 0.5, pos.getZ() + direction.getStepZ() * 0.6 + 0.5, 120, 0.3 - Math.abs(direction.getStepX() * 0.3), 0.3 - Math.abs(direction.getStepY() * 0.3), 0.3 - Math.abs(direction.getStepZ() * 0.3), 0);
                }
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.getCooldowns().addCooldown(this, 40);
                stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, state.setValue(Chiselable.CARVING_STATE, 0).setValue(Chiselable.RUNE, rune), 3);
                return InteractionResult.SUCCESS;

            default:
                return InteractionResult.FAIL;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack,Level level, List<Component> components, TooltipFlag flag) {
        if (stack.getOrCreateTag().getString("rune").equals("")) {
            stack.getOrCreateTag().putString("rune", OnyxChisel.DEFAULT_RUNE);
        }
        components.add(
                Component.translatable("chisel.onyxarcanix.currentRune",
                Component.translatable("rune.onyxarcanix." + stack.getOrCreateTag().getString("rune")).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY)
        );
        components.add(
                Component.translatable("chisel.onyxarcanix.description",
                KeyBindings.CYCLE_RUNE.getKey().getDisplayName().copy().withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY)
        );

    }
}
