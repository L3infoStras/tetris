package scalatris.lib


sealed abstract trait ShapeKind {
  val rotation: ShapeKind
}

case object IShapeKind0 extends ShapeKind {
  val rotation = IShapeKind1
}

case object IShapeKind1 extends ShapeKind {
  val rotation = IShapeKind0
}

case object OShapeKind extends ShapeKind {
  val rotation = OShapeKind
}

case object JShapeKind0 extends ShapeKind {
  val rotation = JShapeKind1
}

case object JShapeKind1 extends ShapeKind {
  val rotation = JShapeKind2
}

case object JShapeKind2 extends ShapeKind {
  val rotation = JShapeKind3
}

case object JShapeKind3 extends ShapeKind {
  val rotation = JShapeKind0
}


class Shape (_x: Int, _y: Int, k: ShapeKind) {
  var x = _x
  var y = _y

  val cells: List[(Int, Int)] = k match {
    case IShapeKind0 => List((0, 0), (0, 1), (0, 2), (0, -1))
    case IShapeKind1 => List((0, 0), (1, 0), (2, 0), (-1, 0))
    case OShapeKind => List((0, 0), (1, 0), (1, 1), (0, 1))
    case JShapeKind0 => List((0, 0), (0, 1), (0, -1), (-1, 1))
    case JShapeKind1 => List((0, 0), (1, 0), (-1, 0), (-1, -1))
    case JShapeKind2 => List((0, 0), (0, -1), (1, -1), (0, 1))
    case JShapeKind3 => List((0, 0), (0, 0), (1, 0), (-1, 0), (-1, 1))
  }

  def rotation = new Shape(x, y, k.rotation)

  /*def makeMove (dir:Direction) {
    x = x + dir.x
    y = y + dir.y
  }*/

  def makeMove (dir: Direction) {
    new Shape (x + dir.x, y + dir.y, k)
  }
}
