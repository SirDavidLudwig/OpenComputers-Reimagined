package li.cil.oc.client

import li.cil.oc.Settings
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.{AtlasTexture, TextureAtlasSprite}
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

import scala.collection.mutable

object Textures {

//  object Font extends TextureBundle {
//    val Aliased = L("chars_aliased")
//    val AntiAliased = L("chars")
//
//    override protected def basePath = "textures/font/%s.png"
//    override protected def loader(map: AtlasTexture, loc: ResourceLocation) = Textures.bind
//  }

//  def getSprite(location: String): TextureAtlasSprite = Minecraft.getInstance.getAtlasSpriteGetter(location)
//
//  def getSprite(location: ResourceLocation): TextureAtlasSprite = Minecraft.getInstance.getAtlasSpriteGetter(location)()

//  object Block extends TextureBundle {
//    val CaseFrontOn = L("overlay/case_front_on")
//
//    def bind(): Unit = Textures.bind(AtlasTexture.LOCATION_BLOCKS_TEXTURE)
//
//    override protected def basePath = "blocks/%s"
//    override protected def loader(map: AtlasTexture, loc: ResourceLocation) = map.loadTexture()
//  }

  @SubscribeEvent
  def onTextureStitchPre(e: TextureStitchEvent.Pre): Unit = {

  }

  abstract class TextureBundle {
    private val locations = mutable.ArrayBuffer.empty[ResourceLocation]

    protected def textureManager = Minecraft.getInstance.getTextureManager

    final def init(map: AtlasTexture): Unit = {
      locations.foreach(loader(map, _))
    }

    protected def L(name: String, load: Boolean = true): Unit = {
      val location = new ResourceLocation(Settings.resourceDomain, String.format(basePath, name))
      if (load) locations += location
      location
    }

    protected def basePath: String

    protected def loader(map: AtlasTexture, loc: ResourceLocation): Unit
  }
}
