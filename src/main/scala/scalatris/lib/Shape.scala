package scalatris.lib

import java.awt.{Color => AWTColor}


sealed abstract trait ShapeKind {
  val color: AWTColor
  val cells: List[List[(Int, Int)]]
  def toString: String
}

case object IShapeKind extends ShapeKind {
  val color = new AWTColor(0, 200, 100)
  val cells = List(
    List((0, 0), (0, 1), (0, 2), (0, -1)),
    List((0, 0), (1, 0), (2, 0), (-1, 0)),
    List((0, 0), (0, 1), (0, 2), (0, -1)),
    List((0, 0), (1, 0), (2, 0), (-1, 0))
  )
  override def toString = "I"
}

case object OShapeKind extends ShapeKind {
  val color = new AWTColor(255, 10, 10)
  val cells = List(
    List((0, 0), (1, 0), (1, 1), (0, 1)),
    List((0, 0), (1, 0), (1, 1), (0, 1)),
    List((0, 0), (1, 0), (1, 1), (0, 1)),
    List((0, 0), (1, 0), (1, 1), (0, 1))
  )
  override def toString = "O"

}

case object JShapeKind extends ShapeKind {
  val color = new AWTColor(20, 20, 255)
  val cells = List(
    List((0, 0), (0, 1), (0, -1), (-1, 1)),
    List((0, 0), (1, 0), (-1, 0), (-1, -1)),
    List((0, 0), (0, -1), (1, -1), (0, 1)),
    List((0, 0), (1, 0), (-1, 0), (1, 1))
  )
  override def toString = "J"
}

case object LShapeKind extends ShapeKind {
  val color = new AWTColor(20, 20, 255)
  val cells = List(
    List((0, 0), (0, -1), (0, 1), (1, 1)),
    List((0, 0), (1, 0), (-1, 0), (-1, 1)),
    List((0, 0), (0, 1), (0, -1), (-1, -1)),
    List((0, 0), (1, 0), (-1, 0), (1, -1))
  )
  override def toString = "L"
}

case object TShapeKind extends ShapeKind {
  val color = new AWTColor(150, 50, 50)
  val cells = List(
    List((0, 0), (1, 0), (-1, 0), (0, -1)),
    List((0, 0), (1, 0), (0, 1), (0, -1)),
    List((0, 0), (1, 0), (-1, 0), (0, 1)),
    List((0, 0), (0, -1), (0, 1), (-1, 0))
  )
  override def toString = "T"
}

case object ZShapeKind extends ShapeKind {
  val color = new AWTColor(20, 239, 14)
  val cells = List(
    List((0, 0), (0, 1), (1, 1), (-1, 0)),
    List((0, 0), (-1, 0), (-1, 1), (0, -1)),
    List((0, 0), (0, 1), (1, 1), (-1, 0)),
    List((0, 0), (-1, 0), (-1, 1), (0, -1))
  )
  override def toString = "Z"
}

case object SShapeKind extends ShapeKind {
  val color = new AWTColor(20, 239, 14)
  val cells = List(
    List((0, 0), (-1, 0), (0, -1), (1, -1)),
    List((0, 0), (0, -1), (1, 0), (1, 1)),
    List((0, 0), (-1, 0), (0, -1), (1, -1)),
    List((0, 0), (0, -1), (1, 0), (1, 1))
  )
  override def toString = "S"
}


class Shape (_x: Int, _y: Int, k: ShapeKind, ki: Int) {
  var x = _x
  var y = _y

  val shapeKindIndex: Int = ki % 4

  val cells = k.cells.apply(shapeKindIndex)
  val color = k.color

  def equals (s:Shape) = (cells == s.cells)

  def rotation = new Shape(x, y, k, shapeKindIndex + 1)

  override def toString: String = k.toString

  /*def makeMove (dir:Direction) {
    x = x + dir.x
    y = y + dir.y
  }*/

  def makeMove (dir: Direction): Shape = {
    dir match {
      case Rotation => rotation
      case _ => new Shape (x + dir.x, y + dir.y, k, shapeKindIndex)
    }
  }
}
