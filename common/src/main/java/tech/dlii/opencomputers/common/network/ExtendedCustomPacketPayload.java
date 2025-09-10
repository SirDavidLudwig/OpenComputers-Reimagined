package tech.dlii.opencomputers.common.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface ExtendedCustomPacketPayload extends CustomPacketPayload {
    void handle(NetworkManager.PacketContext context);
}
