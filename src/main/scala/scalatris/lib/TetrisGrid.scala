package scalatris.lib

import Array.ofDim
import scala.util.Random



class TetrisGrid(_nbCols:Int, _nbRows:Int) {
  val nbCols:Int = _nbCols + 2
  val nbRows:Int = _nbRows + 2

  val shapeKinds = Array(IShapeKind0, OShapeKind, JShapeKind0)

  var blocks = ofDim[Boolean](nbCols, nbRows)
  var shape: Shape = new Shape(nbCols/2, 1, shapeKinds.apply(Random.nextInt(3)))

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

/*  def this = {
    this(12,22)
  }*/

  def newShape {
    shape = new Shape(nbCols/2, 0, shapeKinds.apply(Random.nextInt(3)))
  }

  def fixShape {
    shapeAbsoluteCoords.foreach {
      c => blocks(c._1)(c._2) = true
    }
  }

  def move(dir: Direction) {
    dir match {
      case DirDown => {
        if (moveIsPossible(dir))
          shape.makeMove(DirDown)
        else {
          fixShape
          newShape
        }
      }
      case Rotation => {
        if (rotationIsPossible)
          shape.makeMove(Rotation)
      }
      case DirRight | DirLeft | NoMove => {
        if (moveIsPossible(dir))
          shape.makeMove(dir)
      }
    }
  }

  def shapeAbsoluteCoords: List[(Int, Int)] = {
    shape.cells.map(t => (shape.x + t._1, shape.y + t._2))
  }

  def computeMove (dir: Direction): List[(Int, Int)] = {
    shapeAbsoluteCoords.map(t => (t._1 + dir.x, t._2 + dir.y))
  }

  def computeRotation: List[(Int, Int)] = {
    shape.rotation.cells.map(t => (shape.x + t._1, shape.y + t._2))
  }

  def rotationIsPossible: Boolean = {
    !(computeRotation.map(t =>
      blocks(t._1)(t._2)).reduceLeft(_||_))
  }


  def moveIsPossible(dir: Direction): Boolean = {
    !(computeMove(dir).map(t =>
      blocks(t._1)(t._2)).reduceLeft(_||_))
  }

  def fall {
    shape.makeMove(DirDown)
  }

  def rotate {
    if (rotationIsPossible)
      shape = shape.rotation
  }

  def lineIsFull(y: Int) = blocks.map(_(y)).reduceLeft(_&&_)

/*  def dropLine(y: Int) {

    for (i <- y to 1 by -1)
      for(j <- 1 to nbCols-1)

    blocks.map(_(0)).map(_ && false)
  }

  def checkLines {
    for (j <- 1 until nbRows-1)
      if (lineIsFull(j))
        dropLine(j)
  }
  */

}
