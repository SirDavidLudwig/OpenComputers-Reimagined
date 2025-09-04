package tech.dlii.opencomputers.client.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import tech.dlii.opencomputers.api.API;

import java.util.HashMap;
import java.util.Map;

public final class CustomRenderPipelines {

    // Sampler0 -> Color Texture
    // Sampler1 -> Overlay Texture
    // Sampler2 -> lightmap texture

    public static final Map<ResourceLocation, RenderPipeline> PIPELINES_BY_LOCATION = new HashMap<>();

    public static final RenderPipeline.Snippet TEXT_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
            .withBlend(BlendFunction.TRANSLUCENT)
            .withoutBlend()
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS)
            .buildSnippet();

    public static final RenderPipeline SCREEN_TEXT = register(
            RenderPipeline.builder(TEXT_SNIPPET, RenderPipelines.FOG_SNIPPET)
                    .withLocation(API.MOD_ID + "/pipeline/screen_text")
                    .withVertexShader("core/rendertype_text")
                    .withFragmentShader("core/rendertype_text")
                    .withSampler("Sampler0")
                    .withSampler("Sampler2")
                    .withDepthBias(-1.0F, -10.0F)
                    .build()
    );

    private static RenderPipeline register(RenderPipeline renderPipeline) {
        PIPELINES_BY_LOCATION.put(renderPipeline.getLocation(), renderPipeline);
        return renderPipeline;
    }

    private CustomRenderPipelines() {
    }
}
