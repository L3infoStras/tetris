package scalatris.lib

sealed abstract trait Direction {
  val x = 0
  val y = 0
  def toString: String
}

case object DirRight extends Direction {
  override val x = 1
  override def toString = "right"
}

case object DirLeft extends Direction {
  override val x = -1
  override def toString = "left"
}

case object DirDown extends Direction {
  override val y = 1
  override def toString = "down"
}

case object Rotation extends Direction {
  override def toString = "rotate"
}
