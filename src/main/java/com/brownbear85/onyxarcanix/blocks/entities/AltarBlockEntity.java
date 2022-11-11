package com.brownbear85.onyxarcanix.blocks.entities;

import com.brownbear85.onyxarcanix.blocks.AltarBlock;
import com.brownbear85.onyxarcanix.blocks.ChiselableBlock;
import com.brownbear85.onyxarcanix.blocks.PedestalBlock;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import com.brownbear85.onyxarcanix.init.BlockInit;
import com.brownbear85.onyxarcanix.recipe.AltarRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Locale;
import java.util.Optional;

public class AltarBlockEntity extends ItemHolderBlockEntity {
    public static int PEDESTAL_DISTANCE = 3;

    public BlockState northBlock, southBlock, eastBlock, westBlock;
    public ItemStack northItem, southItem, eastItem, westItem;

    public Type variant;
    public enum Type {
        STONE, ONYX
    }

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ALTAR_BLOCK_ENTITY.get(), pos, state);
        if (state.getBlock().equals(BlockInit.ALTAR.get())) {
            this.variant = Type.STONE;
        } else {
            this.variant = Type.ONYX;
        }
    }

    /* recipe management */

    public static void tick(Level level, BlockPos pos, BlockState state, AltarBlockEntity entity) {
        if (entity.isValid()) {
            Optional<AltarRecipe> recipe = entity.getRecipe();
            if (recipe.isPresent()) {
                entity.setItem(recipe.get().getResultItem());
                entity.setPedestalItem(Direction.NORTH, ItemStack.EMPTY);
                entity.setPedestalItem(Direction.SOUTH, ItemStack.EMPTY);
                entity.setPedestalItem(Direction.EAST, ItemStack.EMPTY);
                entity.setPedestalItem(Direction.WEST, ItemStack.EMPTY);
            }
        }
    };

    public Optional<AltarRecipe> getRecipe() {
        refreshItems();
        return level.getRecipeManager().getRecipeFor(AltarRecipe.Type.INSTANCE, new SimpleContainer(this.getRenderStack(), northItem, southItem, westItem, eastItem), level);
    }

    public void refreshBlocks() {
        BlockPos pos = this.getBlockPos();
        northBlock = level.getBlockState(pos.north(PEDESTAL_DISTANCE));
        southBlock = level.getBlockState(pos.south(PEDESTAL_DISTANCE));
        eastBlock = level.getBlockState(pos.east(PEDESTAL_DISTANCE));
        westBlock = level.getBlockState(pos.west(PEDESTAL_DISTANCE));
    }

    public void refreshItems() {
        if (isValid()) {
            northItem = getPedestalItem(Direction.NORTH);
            southItem = getPedestalItem(Direction.SOUTH);
            eastItem = getPedestalItem(Direction.EAST);
            westItem = getPedestalItem(Direction.WEST);
        }
    }

    public boolean isValid(Direction direction) {
        refreshBlocks();
        return switch (direction) {
            case NORTH -> northBlock.getBlock() instanceof PedestalBlock;
            case SOUTH -> southBlock.getBlock() instanceof PedestalBlock;
            case EAST -> eastBlock.getBlock() instanceof PedestalBlock;
            case WEST -> westBlock.getBlock() instanceof PedestalBlock;
            default -> false;
        };
    }

    public boolean isValid() {
        return isValid(Direction.NORTH) && isValid(Direction.SOUTH) && isValid(Direction.EAST) && isValid(Direction.WEST);
    }

    public PedestalBlockEntity getPedestal(Direction direction) {
        return level.getBlockEntity(this.getBlockPos().offset(direction.getNormal().multiply(PEDESTAL_DISTANCE)), BlockEntityInit.PEDESTAL_BLOCK_ENTITY.get()).get();
    }

    public ItemStack getPedestalItem(Direction direction) {
        return getPedestal(direction).getRenderStack();
    }

    public void setPedestalItem(Direction direction, ItemStack stack) {
        getPedestal(direction).setItem(stack);
    }
}
