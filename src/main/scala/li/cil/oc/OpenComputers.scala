package li.cil.oc

import li.cil.oc.client.renderer.tileentity.CaseRenderer
import li.cil.oc.common.{Proxy => CommonProxy}
import li.cil.oc.client.{Proxy => ClientProxy}
import li.cil.oc.common.init.Blocks
import li.cil.oc.server.{Proxy => ServerProxy}
import li.cil.oc.common.init.RegistrationHandler
import li.cil.oc.common.tileentity.Case
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderType, RenderTypeLookup}
import net.minecraft.client.renderer.tileentity.{TileEntityRenderer, TileEntityRendererDispatcher}
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.{FMLClientSetupEvent, FMLCommonSetupEvent, InterModEnqueueEvent, InterModProcessEvent}
import net.minecraftforge.fml.event.server.{FMLServerStartingEvent, FMLServerStoppedEvent}
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.{LogManager, Logger}

@Mod("opencomputers")
object OpenComputers {

  val ID = "opencomputers"
  val NAME = "OpenComputers"
  val VERSION = "@VERSION"

  val log: Logger = LogManager.getLogger

  // Event Handling -------------------------------------------------------------------------------

  FMLJavaModLoadingContext.get.getModEventBus.addListener(this.setup)
  FMLJavaModLoadingContext.get.getModEventBus.addListener(this.doClientStuff)
  FMLJavaModLoadingContext.get.getModEventBus.register(RegistrationHandler)

  // ----------------------------------------------------------------------------------------------

  // Stupid hack because I can't figure this crap out
  def clientProxy: CommonProxy = new ClientProxy()
  def serverProxy: CommonProxy = new ServerProxy()
  val proxy: CommonProxy = DistExecutor.safeRunForDist(() => () => clientProxy, () => () => serverProxy)

  def setup(event: FMLCommonSetupEvent): Unit = {
    log.debug("OpenComputers setup!")
    proxy.setup
  }

  def doClientStuff(event: FMLClientSetupEvent): Unit = {
    ClientRegistry.bindTileEntityRenderer(Blocks.TILE_ENTITY_CASE, (d) => new CaseRenderer(d).asInstanceOf[TileEntityRenderer[TileEntity]])
    RenderTypeLookup.setRenderLayer(Blocks.CASE_1, RenderType.getCutout)
  }

  def onServerStarting(event: FMLServerStartingEvent): Unit = {

  }

  def onServerStoppingEvent(event: FMLServerStoppedEvent): Unit = {

  }

  def enqueueIMC(event: InterModEnqueueEvent): Unit = {

  }

  def processIMC(event: InterModProcessEvent): Unit = {

  }
}
class OpenComputers {}