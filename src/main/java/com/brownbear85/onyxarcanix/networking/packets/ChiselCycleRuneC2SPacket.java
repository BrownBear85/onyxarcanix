package com.brownbear85.onyxarcanix.networking.packets;

import com.brownbear85.onyxarcanix.items.OnyxChisel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChiselCycleRuneC2SPacket {
    private ItemStack chisel;

    public ChiselCycleRuneC2SPacket(ItemStack chisel) {
        this.chisel = chisel;
    }

    public ChiselCycleRuneC2SPacket(FriendlyByteBuf buf) {
        this.chisel = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.chisel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            chisel = OnyxChisel.cycleRune(chisel, context.getSender());
            context.getSender().setItemInHand(InteractionHand.MAIN_HAND, this.chisel);
        });
        return true;
    }
}
