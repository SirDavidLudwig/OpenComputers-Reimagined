package tech.dlii.opencomputers.common.network.serverbound;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.common.network.ExtendedCustomPacketPayload;
import tech.dlii.opencomputers.common.network.PacketTypes;

public record ToggleComputerPowerPayload(int containerId, boolean power) implements ExtendedCustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ToggleComputerPowerPayload> STREAM_CODEC = CustomPacketPayload.codec(ToggleComputerPowerPayload::write, ToggleComputerPowerPayload::new);

    private ToggleComputerPowerPayload(FriendlyByteBuf buf) {
        this(buf.readVarInt(), buf.readBoolean());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeVarInt(containerId);
        buf.writeBoolean(power);
    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        OpenComputers.LOGGER.info("Toggle computer power");
    }

    @Override
    public Type<? extends ExtendedCustomPacketPayload> type() {
        return PacketTypes.TOGGLE_COMPUTER_POWER;
    }
}
