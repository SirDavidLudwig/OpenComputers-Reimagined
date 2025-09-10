package tech.dlii.opencomputers.common.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.common.network.serverbound.ToggleComputerPowerPayload;

public class PacketTypes {

    public static final CustomPacketPayload.Type<? extends ExtendedCustomPacketPayload> TOGGLE_COMPUTER_POWER = registerServerbound("toggle_computer_power", ToggleComputerPowerPayload.STREAM_CODEC);

    public static void initialize() {
        // NO-OP
    }

    private static <T extends ExtendedCustomPacketPayload> CustomPacketPayload.Type<T> registerClientbound(String name, StreamCodec<FriendlyByteBuf, T> codec) {
        return register(NetworkManager.Side.S2C, name, codec);
    }

    private static <T extends ExtendedCustomPacketPayload> CustomPacketPayload.Type<T> registerServerbound(String name, StreamCodec<FriendlyByteBuf, T> codec) {
        return register(NetworkManager.Side.C2S, name, codec);
    }

    private static <T extends ExtendedCustomPacketPayload> CustomPacketPayload.Type<T> register(NetworkManager.Side side, String name, StreamCodec<FriendlyByteBuf, T> codec) {
        CustomPacketPayload.Type<T> type = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(API.MOD_ID, name));
        if (
                (side == NetworkManager.Side.C2S && Platform.getEnv() == EnvType.CLIENT)
                || (side == NetworkManager.Side.S2C && Platform.getEnv() == EnvType.SERVER)
        ){
            NetworkManager.registerReceiver(side, type, codec, (buf, context) -> buf.handle(context));
        }
        return type;
    }
}
