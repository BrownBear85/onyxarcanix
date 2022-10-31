package com.brownbear85.onyxarcanix.blocks;

import com.brownbear85.onyxarcanix.blocks.entities.ItemHolderBlockEntity;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BaseItemHolderBlock extends BaseEntityBlock {
    public float itemX, itemY, itemZ;

    public BaseItemHolderBlock(BlockBehaviour.Properties properties, float itemX, float itemY, float itemZ) {
        super(properties);
        this.itemX = itemX;
        this.itemY = itemY;
        this.itemZ = itemZ;
    }

    /* override methods */

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
        level.getBlockEntity(pos, BlockEntityInit.ITEM_HOLDER_BLOCK_ENTITY.get()).get().setItem(player, hand);
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.ITEM_HOLDER_BLOCK_ENTITY.get().create(pos, state);
    }

    /*
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : BaseEntityBlock.createTickerHelper(type, BlockEntityInit.ITEM_HOLDER_BLOCK_ENTITY.get(), ItemHolderBlockEntity::tick);
    }
     */

}
