package com.brownbear85.onyxarcanix.blocks.entities;

import com.brownbear85.onyxarcanix.init.BlockEntityInit;
import com.brownbear85.onyxarcanix.networking.ModNetworking;
import com.brownbear85.onyxarcanix.networking.packets.ItemStackSyncC2SPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemHolderBlockEntity extends BlockEntity {
    public ItemHolderBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ITEM_HOLDER_BLOCK_ENTITY.get(), pos, state);
    }

    /*
    public static void tick(Level level, BlockPos pos, BlockState state, ItemHolderBlockEntity entity) {

    }
     */

    /* item management */

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            if (!level.isClientSide())
                ModNetworking.sendToClients(new ItemStackSyncC2SPacket(this, getBlockPos()));
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    private final LazyOptional<IItemHandlerModifiable> optional = LazyOptional.of(() -> this.itemHandler);

    public void dropItem() {
        ItemEntity itementity = new ItemEntity(this.level, (double)this.getBlockPos().getX() + 0.5D, (double)this.getBlockPos().getY() + 1.2D, (double)this.getBlockPos().getZ() + 0.5D, this.itemHandler.getStackInSlot(0));
        itementity.setDeltaMovement(this.level.random.nextDouble() * 0.02D, 0.05D, this.level.random.nextDouble() * 0.02D);
        this.level.addFreshEntity(itementity);
    }

    public void setItem(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.hasItem()) {
            removeItem(player, hand);
        } else if (!this.level.isClientSide()) {
            ItemStack newStack = itemHandler.insertItem(0, stack, false);
            player.setItemInHand(hand, newStack);
        }
    }

    public void removeItem(Player player, InteractionHand hand) {
        if (!this.level.isClientSide()) {
            ItemStack stack = itemHandler.extractItem(0, 1, false);
            if (player.getItemInHand(hand) == ItemStack.EMPTY) {
                player.setItemInHand(hand, stack);
            } else if (!player.addItem(stack)) {
                dropItem();
            }
        }
    }

    public boolean hasItem() {
        return itemHandler.getStackInSlot(0) != ItemStack.EMPTY;
    }

    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(0);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            this.itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    /* nbt */

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.itemHandler.deserializeNBT(nbt.getCompound("item"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Item", this.itemHandler.serializeNBT());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        this.optional.invalidate();
    }

    /* these make the item still render when the chunk is loaded */

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
