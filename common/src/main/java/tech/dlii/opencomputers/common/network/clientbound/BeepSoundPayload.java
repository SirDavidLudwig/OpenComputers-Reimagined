package tech.dlii.opencomputers.common.network.clientbound;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import tech.dlii.opencomputers.common.network.ExtendedCustomPacketPayload;
import tech.dlii.opencomputers.common.network.PacketTypes;

public record BeepSoundPayload(ResourceLocation level, double x, double y, double z, int frequency, int duration) implements ExtendedCustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, BeepSoundPayload> STREAM_CODEC = CustomPacketPayload.codec(BeepSoundPayload::write, BeepSoundPayload::new);

    public BeepSoundPayload(Level level, double x, double y, double z, int frequency, int duration) {
        this(level.dimension().location(), x, y, z, frequency, duration);
    }

    private BeepSoundPayload(FriendlyByteBuf buf) {
        this(buf.readResourceLocation(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(level);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(frequency);
        buf.writeInt(duration);
    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        if (context.getPlayer().level().dimension().location() != level) {
            return;
        }
    }

    @Override
    public Type<? extends ExtendedCustomPacketPayload> type() {
        return PacketTypes.BEEP_SOUND;
    }
}

