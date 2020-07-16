package li.cil.oc.common

import li.cil.oc.server.fs
import li.cil.oc.{CreativeTab, OpenComputers, Settings, api}
import li.cil.oc.common.init.{Blocks, Items}
import net.minecraftforge.common.MinecraftForge

class Proxy {
  def setup(): Unit = {
    MinecraftForge.EVENT_BUS.register(this)

    OpenComputers.log.debug("Initializing blocks and items.")

//    Blocks.init()
//    Items.init()

    api.ItemGroup.instance = CreativeTab
//    api.API.driver = driver.Registry
    api.API.fileSystem = fs.FileSystem
    api.API.items = Items
//    api.API.machine = machine.Machine
//    api.API.nanomachines = nanomachines.Nanomachines
//    api.API.network = network.Network
//    api.API.config = Settings.get.config
  }
}