package li.cil.oc.common.tileentity.traits

import li.cil.oc.api.internal

trait Colored extends TileEntity with internal.Colored{
  private var _color = 0

  def consumesDye = false

  override def getColor: Int = _color

  override def setColor(value: Int): Unit = if (value != _color) {
    _color = value
    onColorChanged()
  }

  override def controlsConnectivity = false

  protected def onColorChanged(): Unit = {
//    if (getWorld != null && !world.isRemote) {
      // Send color change event
//    }
  }
}
