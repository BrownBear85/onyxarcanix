package com.brownbear85.onyxarcanix.blocks;

import com.brownbear85.onyxarcanix.blocks.entities.ItemHolderBlockEntity;
import com.brownbear85.onyxarcanix.blocks.entities.PedestalBlockEntity;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ItemHolderBlock extends BaseEntityBlock {
    public float itemX, itemY, itemZ;

    public BlockEntityType<?> type = null;

    public ItemHolderBlock(BlockBehaviour.Properties properties, float itemX, float itemY, float itemZ) {
        super(properties);
        this.itemX = itemX;
        this.itemY = itemY;
        this.itemZ = itemZ;
    }

    /* override methods */

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity entity = pLevel.getBlockEntity(pPos, type).get();
        if (entity instanceof ItemHolderBlockEntity itemHolder && itemHolder.hasItem()) {
            return itemHolder.getRenderStack();
        } else {
            return super.getCloneItemStack(pLevel, pPos, pState);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ItemHolderBlockEntity) {
                ((ItemHolderBlockEntity) blockEntity).dropItem();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ItemHolderBlockEntity) {
            ((ItemHolderBlockEntity) blockEntity).setItem(player, hand);
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        try {return this.type.create(pos, state);}
        catch (Exception e) {
            System.out.println("you probably need to set the type manually in ClientEvents.java");
        }
        return this.type.create(pos, state);
    }
}
