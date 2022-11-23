package com.brownbear85.onyxarcanix.networking.packets;

import com.brownbear85.onyxarcanix.items.Wand;
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

public class TestC2SPacket {
    private ItemStack wand;

    public TestC2SPacket(ItemStack wand) {
        this.wand = wand;
    }

    public TestC2SPacket(FriendlyByteBuf buf) {
        this.wand = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(wand);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            player.setItemInHand(InteractionHand.MAIN_HAND, Wand.addSpell(wand, "cheese"));
        });
        return true;
    }
}
