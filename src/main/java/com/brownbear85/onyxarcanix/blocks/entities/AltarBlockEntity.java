package com.brownbear85.onyxarcanix.blocks.entities;

import com.brownbear85.onyxarcanix.blocks.PedestalBlock;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AltarBlockEntity extends ItemHolderBlockEntity {
    public static int PEDESTAL_DISTANCE = 3;

    public BlockState northBlock, southBlock, eastBlock, westBlock;
    public ItemStack northItem, southItem, eastItem, westItem;

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ALTAR_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AltarBlockEntity entity) {
        entity.refreshBlocks();
    };

    public void refreshBlocks() {
        BlockPos pos = this.getBlockPos();
        northBlock = level.getBlockState(pos.north(PEDESTAL_DISTANCE));
        southBlock = level.getBlockState(pos.south(PEDESTAL_DISTANCE));
        eastBlock = level.getBlockState(pos.east(PEDESTAL_DISTANCE));
        westBlock = level.getBlockState(pos.west(PEDESTAL_DISTANCE));
    }

    public boolean isValid(Direction direction) {
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

    public ItemStack getPedestalItem(Direction direction) {
        PedestalBlockEntity pedestal = level.getBlockEntity(this.getBlockPos().offset(direction.getNormal().multiply(PEDESTAL_DISTANCE)), BlockEntityInit.PEDESTAL_BLOCK_ENTITY.get()).get();
        return pedestal.getRenderStack();
    }
}
