package scalatris.lib

sealed abstract trait Direction {
  val x = 0
  val y = 0
}

case object DirRight extends Direction {
  override val x = 1
}

case object DirLeft extends Direction {
  override val x = -1
}

case object DirDown extends Direction {
  override val y = 1
}
