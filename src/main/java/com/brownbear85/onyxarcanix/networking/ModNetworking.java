package com.brownbear85.onyxarcanix.networking;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.networking.packets.ChiselCycleRuneC2SPacket;
import com.brownbear85.onyxarcanix.networking.packets.TestC2SPacket;
import com.brownbear85.onyxarcanix.networking.packets.ItemStackSyncC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(OnyxArcanix.MODID, "networking"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(TestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TestC2SPacket::new)
                .encoder(TestC2SPacket::toBytes)
                .consumerMainThread(TestC2SPacket::handle)
                .add();

        net.messageBuilder(ItemStackSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackSyncC2SPacket::new)
                .encoder(ItemStackSyncC2SPacket::toBytes)
                .consumerMainThread(ItemStackSyncC2SPacket::handle)
                .add();

        net.messageBuilder(ChiselCycleRuneC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChiselCycleRuneC2SPacket::new)
                .encoder(ChiselCycleRuneC2SPacket::toBytes)
                .consumerMainThread(ChiselCycleRuneC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
