package tech.dlii.opencomputers.client.renderer;

import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import tech.dlii.opencomputers.api.API;

import java.util.function.Function;

public abstract class CustomRenderTypes extends RenderType {

    private static final RenderType SCREEN_TEXT_BACKGROUND = create(
            API.MOD_ID + "/screen_text_background",
            1536,
            false,
            true,
            CustomRenderPipelines.SCREEN_TEXT_BACKGROUND,
            RenderType.CompositeState.builder()
                    .setTextureState(NO_TEXTURE)
                    .setLightmapState(LIGHTMAP)
                    .createCompositeState(true)
    );

    private static final Function<ResourceLocation, RenderType> SCREEN_TEXT = Util.memoize(
            (resourceLocation -> create(
                    API.MOD_ID + "/screen_text",
                    1536,
                    false,
                    true,
                    CustomRenderPipelines.SCREEN_TEXT,
                    RenderType.CompositeState.builder()
                            .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false))
                            .setLightmapState(LIGHTMAP)
                            .createCompositeState(false)
            ))
    );

    public static RenderType screenTextBackground() {
        return SCREEN_TEXT_BACKGROUND;
    }

    public static RenderType screenText(ResourceLocation resourceLocation) {
        return SCREEN_TEXT.apply(resourceLocation);
    }

    private CustomRenderTypes(String string, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, i, bl, bl2, runnable, runnable2);
    }
}
