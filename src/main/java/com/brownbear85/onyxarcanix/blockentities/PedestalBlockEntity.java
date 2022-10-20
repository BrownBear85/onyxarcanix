package com.brownbear85.onyxarcanix.blockentities;

import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends BlockEntity {
    public static class PedestalStackHandler extends ItemStackHandler {
        public PedestalStackHandler(int size) {
            super(size);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    }
    private final PedestalStackHandler inventory = new PedestalStackHandler(1);
    private final LazyOptional<IItemHandlerModifiable> optional = LazyOptional.of(() -> this.inventory);

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.PEDESTAL.get(), pos, state);
    }

    public ItemStack getRenderStack() {
        return inventory.getStackInSlot(0);
    }

    public void setItem(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.hasItem()) {
            removeItem(player, hand);
        } else if (!this.level.isClientSide()) {
            if (!player.isCreative()) {
                player.setItemInHand(hand, inventory.insertItem(0, stack, false));
            }
        }
    }

    public void removeItem(Player player, InteractionHand hand) {
        if (!this.level.isClientSide()) {
            ItemStack stack = inventory.extractItem(0, 1, false);
            if (player.isCreative() && player.getInventory().contains(stack)) {
                return;
            }
            if (!player.addItem(stack)) {
                ItemEntity itementity = new ItemEntity(this.level, (double)this.getBlockPos().getX() + 0.5D, (double)this.getBlockPos().getY() + 0.1D, (double)this.getBlockPos().getZ() + 0.5D, stack);
                itementity.setDeltaMovement(this.level.random.nextDouble() * 0.02D, 0.05D, this.level.random.nextDouble() * 0.02D);
                this.level.addFreshEntity(itementity);
            }
        }
    }

    public boolean hasItem() {
        return inventory.getStackInSlot(0) != ItemStack.EMPTY;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Inventory", this.inventory.serializeNBT());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        this.optional.invalidate();
    }
}
