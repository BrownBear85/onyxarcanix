package com.brownbear85.onyxarcanix.blocks.entities;

import com.brownbear85.onyxarcanix.blocks.PedestalBlock;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import com.brownbear85.onyxarcanix.recipe.AltarRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class AltarBlockEntity extends ItemHolderBlockEntity {
    public static int PEDESTAL_DISTANCE = 3;

    public BlockState northBlock, southBlock, eastBlock, westBlock;
    public ItemStack northItem, southItem, eastItem, westItem;

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ALTAR_BLOCK_ENTITY.get(), pos, state);
    }

    public String type = "cheese"; // pull from blockstate

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
        return level.getRecipeManager().getRecipeFor(AltarRecipe.Type.INSTANCE, new SimpleContainer(this.getRenderStack(), northItem, southItem, eastItem, westItem), level);
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
            northItem = getItem(Direction.NORTH);
            southItem = getItem(Direction.SOUTH);
            eastItem = getItem(Direction.EAST);
            westItem = getItem(Direction.WEST);
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

    public ItemStack getItem(Direction direction) {
        return getPedestal(direction).getRenderStack();
    }

    public void setPedestalItem(Direction direction, ItemStack stack) {
        getPedestal(direction).setItem(stack);
    }

    /* blockstates */

    /* nbt */

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        type = nbt.getString("type");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putString("type", type);
    }
}
