package li.cil.oc.client.renderer.tileentity

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import li.cil.oc.client.renderer.RenderUtil
import li.cil.oc.common.tileentity.Case
import net.minecraft.block.HorizontalBlock
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.{IRenderTypeBuffer, RenderState, RenderType}
import net.minecraft.client.renderer.tileentity.{TileEntityRenderer, TileEntityRendererDispatcher}
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.{Direction, ResourceLocation}
import net.minecraft.util.math.vector.{Matrix3f, Matrix4f, Vector3f}
import org.lwjgl.opengl.GL11


object CaseRenderer {
  def getRenderType(location: ResourceLocation): RenderType = {
    val renderType = RenderType.State.getBuilder()
      .texture(new RenderState.TextureState(location, false, false))
      .diffuseLighting(new RenderState.DiffuseLightingState(true))
      .lightmap(new RenderState.LightmapState(false))
      .overlay(new RenderState.OverlayState(false))
      .alpha(new RenderState.AlphaState(0.003921569F))
      .build(true)
      RenderType.makeType("case_overlay", DefaultVertexFormats.POSITION_TEX, GL11.GL_QUADS, 256, true, false, renderType)
  }
}
class CaseRenderer(dispatcher: TileEntityRendererDispatcher) extends TileEntityRenderer[Case](dispatcher) {

  val CaseFrontOn = new ResourceLocation("opencomputers:textures/blocks/overlay/case_front_on.png")
  val CaseFrontActivity = new ResourceLocation("opencomputers:textures/blocks/overlay/case_front_activity.png")
  val CaseFrontError = new ResourceLocation("opencomputers:textures/blocks/overlay/case_front_error.png")

  override def render(computer: Case, partialTicks: Float, matrixStack: MatrixStack, buffer: IRenderTypeBuffer, combinedLight: Int, combinedOverlay: Int): Unit = {
    matrixStack.push()

    matrixStack.translate(0.5, 0, 0.5)
    computer.getBlockState.get(HorizontalBlock.HORIZONTAL_FACING) match {
        case Direction.WEST => matrixStack.rotate(Vector3f.YP.rotationDegrees(90))
        case Direction.SOUTH => matrixStack.rotate(Vector3f.YP.rotationDegrees(180))
        case Direction.EAST => matrixStack.rotate(Vector3f.YP.rotationDegrees(-90))
        case _ =>
    }
    matrixStack.translate(-0.5, 0, -0.505)

    val matrixPos = matrixStack.getLast.getMatrix
    val matrixNormal = matrixStack.getLast.getNormal

    if (computer.isRunning) {
      renderFrontOverlay(CaseFrontOn, buffer, matrixPos, matrixNormal)
      if (System.currentTimeMillis() - computer.lastFileSystemAccess < 400 && computer.world.rand.nextDouble() > 0.1) {
        renderFrontOverlay(CaseFrontActivity, buffer, matrixPos, matrixNormal)
      }
    }
    else if (computer.hasErrored && RenderUtil.shouldShowErrorLight(computer.hashCode)) {
      renderFrontOverlay(CaseFrontError, buffer, matrixPos, matrixNormal)
    }

    matrixStack.pop()
  }

  private def renderFrontOverlay(texture: ResourceLocation, buffer: IRenderTypeBuffer, matrixPos: Matrix4f, matrixNormal: Matrix3f): Unit = {
    val builder = buffer.getBuffer(CaseRenderer.getRenderType(texture))
    addQuad(builder, matrixPos, matrixNormal)
  }

  def addQuad(builder: IVertexBuilder, matrixPos: Matrix4f, matrixNormal: Matrix3f): Unit = {
    addVertex(builder, matrixPos, matrixNormal, 1, 1, 1, 0.1f, 0, 1, 0, 1, 0)
    addVertex(builder, matrixPos, matrixNormal, 1, 1, 1, 0.1f, 1, 1, 0, 0, 0)
    addVertex(builder, matrixPos, matrixNormal, 1, 1, 1, 0.1f, 1, 0, 0, 0, 1)
    addVertex(builder, matrixPos, matrixNormal, 1, 1, 1, 0.1f, 0, 0, 0, 1, 1)
  }

  /**
   * Borrowed from net.minecraft.client.renderer.tileentity.BeaconTileEntityRenderer
   */
  private def addVertex(buffer: IVertexBuilder, matrixPos: Matrix4f, matrixNormal: Matrix3f,
                        red: Float, green: Float, blue: Float, alpha: Float,
                        x: Float, y: Float, z: Float, texU: Float, texV: Float): Unit = {
    buffer.pos(matrixPos, x, y, z).color(255, 255, 255, 1).tex(texU, texV).overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(matrixNormal, 1, 0, 0).endVertex()
  }
}