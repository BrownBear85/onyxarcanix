package com.brownbear85.onyxarcanix.blocks;

import com.brownbear85.onyxarcanix.blocks.entities.AltarBlockEntity;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AltarBlock extends ItemHolderBlock {
    public AltarBlock(Properties properties, float itemX, float itemY, float itemZ) {
        super(properties, itemX, itemY, itemZ);
    }

    /* voxel shape */

    private static final VoxelShape SHAPE = makeShape();

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.03125, -0.000625, 0.03125, 0.96875, 0.624375, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0, 1, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.25, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.75, 0.25, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.265625, 0.621875, 0.265625, 0.734375, 0.684375, 0.734375), BooleanOp.OR);
        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    /* override methods */

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntityInit.ALTAR_BLOCK_ENTITY.get(), AltarBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        return super.use(state, level, pos, player, hand, result);
    }

    /* blockstates */

//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//        builder.add(TYPE);
//    }
//
//    @Nullable
//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        return super.getStateForPlacement(context).setValue(TYPE, Types.STONE);
//    }
//
//    public enum Types implements StringRepresentable {
//        STONE("stone"), ONYX("onyx");
//
//        private final String name;
//
//        Types(String name) {
//            this.name = name;
//        }
//
//        public String toString() {
//            return this.name;
//        }
//
//        public String getSerializedName() {
//            return this.name;
//        }
//    }
}
