package scalatris.lib


sealed abstract trait ShapeKind {
  val cells: List[List[(Int, Int)]]
}

case object IShapeKind extends ShapeKind {
  val cells = List(
    List((0, 0), (0, 1), (0, 2), (0, -1)),
    List((0, 0), (1, 0), (2, 0), (-1, 0)),
    List((0, 0), (0, 1), (0, 2), (0, -1)),
    List((0, 0), (1, 0), (2, 0), (-1, 0))
  )
}

case object OShapeKind extends ShapeKind {
  val cells = List(
    List((0, 0), (1, 0), (1, 1), (0, 1)),
    List((0, 0), (1, 0), (1, 1), (0, 1)),
    List((0, 0), (1, 0), (1, 1), (0, 1)),
    List((0, 0), (1, 0), (1, 1), (0, 1))
  )

}

case object JShapeKind extends ShapeKind {
  val cells = List(
    List((0, 0), (0, 1), (0, -1), (-1, 1)),
    List((0, 0), (1, 0), (-1, 0), (-1, -1)),
    List((0, 0), (0, -1), (1, -1), (0, 1)),
    List((0, 0), (1, 0), (-1, 0), (1, 1))
  )

}

case object LShapeKind extends ShapeKind {
  val cells = List(
    List((0, 0), (0, -1), (0, 1), (1, 1)),
    List((0, 0), (1, 0), (-1, 0), (-1, 1)),
    List((0, 0), (0, 1), (0, -1), (-1, -1)),
    List((0, 0), (1, 0), (-1, 0), (1, -1))
  )
}

case object TShapeKind extends ShapeKind {
  val cells = List(
    List((0, 0), (1, 0), (-1, 0), (0, -1)),
    List((0, 0), (1, 0), (0, 1), (0, -1)),
    List((0, 0), (1, 0), (-1, 0), (0, 1)),
    List((0, 0), (0, -1), (0, 1), (-1, 0))
  )
}

case object ZShapeKind extends ShapeKind {
  val cells = List(
    List((0, 0), (0, 1), (1, 1), (-1, 0)),
    List((0, 0), (-1, 0), (-1, 1), (0, -1)),
    List((0, 0), (0, 1), (1, 1), (-1, 0)),
    List((0, 0), (-1, 0), (-1, 1), (0, -1))
  )
}

case object SShapeKind extends ShapeKind {
  val cells = List(
    List((0, 0), (-1, 0), (0, -1), (1, -1)),
    List((0, 0), (0, -1), (1, 0), (1, 1)),
    List((0, 0), (-1, 0), (0, -1), (1, -1)),
    List((0, 0), (0, -1), (1, 0), (1, 1))
  )
}


class Shape (_x: Int, _y: Int, k: ShapeKind, ki: Int) {
  var x = _x
  var y = _y

  val shapeKindIndex: Int = ki % 4

  val cells = k.cells.apply(shapeKindIndex)

  def rotation = new Shape(x, y, k, shapeKindIndex + 1)

  /*def makeMove (dir:Direction) {
    x = x + dir.x
    y = y + dir.y
  }*/

  def makeMove (dir: Direction): Shape = {
    dir match {
      case Rotation => rotation
      case _ => new Shape (x + dir.x, y + dir.y, k)
    }
  }
}
