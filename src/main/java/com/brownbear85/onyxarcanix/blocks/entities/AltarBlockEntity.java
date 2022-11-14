package com.brownbear85.onyxarcanix.blocks.entities;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blocks.PedestalBlock;
import com.brownbear85.onyxarcanix.blocks.entities.renderer.ItemHolderBlockEntityRenderer;
import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import com.brownbear85.onyxarcanix.init.BlockInit;
import com.brownbear85.onyxarcanix.init.ParticleInit;
import com.brownbear85.onyxarcanix.init.SoundInit;
import com.brownbear85.onyxarcanix.recipe.AltarRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class AltarBlockEntity extends ItemHolderBlockEntity {
    public static int PEDESTAL_DISTANCE = 3;

    public BlockState northBlock, southBlock, eastBlock, westBlock;
    public ItemStack northItem, southItem, eastItem, westItem;

    public Type altar;
    public enum Type {
        STONE, ONYX
    }

    public int progress;
    public String currentRecipe = "none";

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ALTAR_BLOCK_ENTITY.get(), pos, state);
        if (state.getBlock().equals(BlockInit.ALTAR.get())) {
            this.altar = Type.STONE;
        } else {
            this.altar = Type.ONYX;
        }
    }

    /* recipe management */

    private static final Direction[] pedestalDirections = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    public static void tick(Level level, BlockPos pos, BlockState state, AltarBlockEntity entity) {
        if (entity.isValid()) {
            Optional<AltarRecipe> recipe = entity.getRecipe();
            if (recipe.isPresent() && entity.canDoRecipe(recipe.get())) {
                if (entity.progress % 20 == 0) {
                    if (level instanceof ServerLevel serverLevel) {
                        double x = entity.getBlockPos().getX() + 0.5D;
                        double y = entity.getBlockPos().getY();
                        double z = entity.getBlockPos().getZ() + 0.5D;
                        serverLevel.playSound(null, entity.getBlockPos(), SoundInit.ALTAR_PROCESSING.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                }

                entity.progress++;
                entity.currentRecipe = recipe.get().getId().toString();

                if (level instanceof ServerLevel serverLevel) {
                    if (entity.progress < recipe.get().getTime() - 68) {
                        for (Direction dir : pedestalDirections) {
                            if (level.random.nextBoolean()) {
                                serverLevel.sendParticles(ParticleInit.ALTAR_PARTICLES.get(),
                                        pos.getX() + 0.5 + dir.getStepX() * (PEDESTAL_DISTANCE - 0.01),
                                        pos.getY() + 1.2,
                                        pos.getZ() + 0.5 + dir.getStepZ() * (PEDESTAL_DISTANCE - 0.01),
                                        1, 0, 0, 0, 0);
                            }
                            serverLevel.sendParticles(ParticleInit.ALTAR_PARTICLES.get(),
                                    pos.getX() + 0.5 + dir.getStepX() * (PEDESTAL_DISTANCE - 0.01),
                                    pos.getY() + 1.2,
                                    pos.getZ() + 0.5 + dir.getStepZ() * (PEDESTAL_DISTANCE - 0.01),
                                    1, 0, 0, 0, 0.05);
                        }
                    }
                    if (entity.progress >= 68) {
                        serverLevel.sendParticles(ParticleInit.ALTAR_PARTICLES.get(),
                                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                                4, 0, 0, 0, 0.1);
                    }
                }
                if (entity.progress >= recipe.get().getTime()) {
                    entity.progress = 0;
                    entity.currentRecipe = "none";

                    if (entity.level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.FLASH,
                                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                                2, 0, 0, 0, 0.1);

                        double x = entity.getBlockPos().getX() + 0.5D;
                        double y = entity.getBlockPos().getY();
                        double z = entity.getBlockPos().getZ() + 0.5D;
                        serverLevel.playSound(null, entity.getBlockPos(), SoundInit.ALTAR_FINISH.get(), SoundSource.BLOCKS, 1.0F, 1.0F);

                    }

                    entity.setItem(recipe.get().getResultItem());
                    entity.setPedestalItem(Direction.NORTH, ItemStack.EMPTY);
                    entity.setPedestalItem(Direction.SOUTH, ItemStack.EMPTY);
                    entity.setPedestalItem(Direction.EAST, ItemStack.EMPTY);
                    entity.setPedestalItem(Direction.WEST, ItemStack.EMPTY);
                }
            } else {
                entity.progress = 0;
                entity.currentRecipe = "none";
            }
        }
    };

    public Optional<AltarRecipe> getRecipe() {
        refreshItems();
        return level.getRecipeManager().getRecipeFor(AltarRecipe.Type.INSTANCE, new SimpleContainer(this.getRenderStack(), northItem, southItem, westItem, eastItem), level);
    }

    public boolean canDoRecipe(AltarRecipe recipe) {
        if (recipe.getAltar() == Type.ONYX && altar == Type.STONE) {
            return false;
        } else {
            return true;
        }
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

    /* nbt */

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        progress = nbt.getInt("progress");
        currentRecipe = nbt.getString("recipe");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("progress", progress);
        nbt.putString("recipe", currentRecipe);
    }
}
