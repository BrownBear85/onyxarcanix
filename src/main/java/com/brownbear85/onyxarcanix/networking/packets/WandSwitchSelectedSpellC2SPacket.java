package com.brownbear85.onyxarcanix.networking.packets;

import com.brownbear85.onyxarcanix.items.Wand;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WandSwitchSelectedSpellC2SPacket {
    private ItemStack wand;
    private int amount;

    public WandSwitchSelectedSpellC2SPacket(ItemStack wand, int amount) {
        this.wand = wand;
        this.amount = amount;
    }

    public WandSwitchSelectedSpellC2SPacket(FriendlyByteBuf buf) {
        this.wand = buf.readItem();
        this.amount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.wand);
        buf.writeInt(this.amount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            wand = Wand.cycleSelected(wand, amount);
            context.getSender().setItemInHand(InteractionHand.MAIN_HAND, wand);
        });
        return true;
    }
}
