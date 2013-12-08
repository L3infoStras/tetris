package scalatris.lib

import Array.ofDim

class Grid(_nbCols:Int, _nbRows:Int) {
  val nbCols:Int = _nbCols + 2
  val nbRows:Int = _nbRows + 2

  var blocks = ofDim[Boolean](nbCols, nbRows)
  var shape: Shape = new Shape((4, 5), List((0, 0), (0, 1), (0, -1), (0, -2), (1, 1)))

  // initialisation de la matrice de la grille
  for (i <- 0 until nbRows-1) {
    blocks(0)(i) = true
  }

  for (i <- 0 until nbRows-1) {
    blocks(nbCols-1)(i) = true
  }

  for (i <- 0 until nbCols-1) {
    blocks(i)(nbRows-1) = true
  }

  // fin initialisation

  blocks(9)(14) = true

  def newShape {
    shape = new Shape((5, 0), List((0, 0), (0, 1), (1, 0), (1, 1)))
  }

  def fixShape {
    shapeAbsoluteCoords.foreach {
      c => blocks(c._1)(c._2) = true
    }
  }

  def move(dir: Direction) {
    dir match {
      case DirLeft | DirRight => {
        if (moveIsPossible(dir))
          shape.makeMove(dir)
      }
      case DirDown => {
        if (moveIsPossible(dir))
          shape.makeMove(DirDown)
        else {
          fixShape
          newShape
        }
      }
    }
  }

  def shapeAbsoluteCoords: List[(Int, Int)] = {
    shape.cells.map(t => (shape.x + t._1, shape.y + t._2))
  }

  def computeMove (dir: Direction): List[(Int, Int)] = {
    shapeAbsoluteCoords.map(t => (t._1 + dir.x, t._2 + dir.y))
  }

  def moveIsPossible(dir: Direction):Boolean = {
     !(computeMove(dir).map(t =>
        blocks(t._1)(t._2)).reduceLeft(_||_))
  }

  def fall {
    shape.makeMove(DirDown)
  }

  def rotate {
    shape.rotate
  }

}
