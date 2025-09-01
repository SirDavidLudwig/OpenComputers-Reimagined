package tech.dlii.opencomputers.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.machine.Architecture;

public record ArchitectureComponent(String className, String name) {
    public static final Codec<ArchitectureComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("class_name").forGetter(ArchitectureComponent::className),
            Codec.STRING.fieldOf("name").forGetter(ArchitectureComponent::name)
    ).apply(instance, ArchitectureComponent::new));

    public static final StreamCodec<ByteBuf, ArchitectureComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ArchitectureComponent::className,
            ByteBufCodecs.STRING_UTF8, ArchitectureComponent::name,
            ArchitectureComponent::new
    );

    public static ArchitectureComponent fromArchitecture(Class<? extends Architecture> ArchitectureClass) {
        return new ArchitectureComponent(
                API.machine.getArchitectureName(ArchitectureClass),
                ArchitectureClass.getName()
        );
    }
}
