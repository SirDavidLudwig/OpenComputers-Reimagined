package li.cil.oc.client

import li.cil.oc.Settings
import net.minecraft.tileentity.TileEntity

object Sound {
  def startLoop(tileEntity: TileEntity, name: String, volume: Float = 1f, delay: Long = 0): Unit = {
//    if (Settings.get.soundVolume > 0) {
//      commandQueue.synchronized {
//        commandQueue += new StartCommand(System.currentTimeMillis() + delay, tileEntity, name, volume)
//      }
//    }
  }

  def stopLoop(tileEntity: TileEntity): Unit = {
//    if (Settings.get.soundVolume > 0) {
//      commandQueue.synchronized {
//        commandQueue += new StopCommand(tileEntity)
//      }
//    }
  }
}
