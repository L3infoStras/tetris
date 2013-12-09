package scalatris.lib

import Array.ofDim
import scala.util.Random



class TetrisGrid(_nbCols:Int, _nbRows:Int) {
  val nbCols:Int = _nbCols + 2
  val nbRows:Int = _nbRows + 2

  val shapeKinds = Array(IShapeKind, OShapeKind, JShapeKind,
    LShapeKind, TShapeKind, SShapeKind, ZShapeKind)

  var blocks = ofDim[Boolean](nbCols, nbRows)
  var shape: Shape = null
  var score: Int = 0

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

  newShape

  // fin initialisation

/*  def this = {
    this(12,22)
  }*/

  def newShape {
    shape = new Shape(nbCols/2, 1,
      shapeKinds.apply(Random.nextInt(shapeKinds.length)),
      0)
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
          shape = shape.makeMove(DirDown)
        else {
          fixShape
          newShape
          checkLines
        }
      }
      case Rotation => {
        if (rotationIsPossible)
          shape = shape.makeMove(Rotation)
      }
      case DirRight | DirLeft | NoMove => {
        if (moveIsPossible(dir))
          shape = shape.makeMove(dir)
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
    while (moveIsPossible(DirDown))
      move(DirDown)
    move(DirDown)
  }

  def rotate {
    if (rotationIsPossible)
      shape = shape.rotation
  }

  def lineIsFull(y: Int): Boolean = blocks.map(_(y)).reduceLeft(_&&_)

  def dropLine(y: Int) {
    for (j <- y to 1 by -1)
      for(i <- 1 to nbCols-2)
        blocks(i)(j) = blocks(i)(j-1)
    for (i <- 1 to nbCols-2)
      blocks(i)(0) = false
  }

  def checkLines {
    var n:Int = 0 // nombre de lignes à supprimer
    for (_ <- 1 to 4) {
      for (j <- nbRows-1 to 1 by -1)
        if (lineIsFull(j)) {
          println("line: " ++ j.toString ++ " is full")
          n += 1
          dropLine(j)
        }
    }
    addScore(n)
    println("Score: " ++ score.toString)
  }

  def addScore(n: Int) {
    score += computeScore(n)
  }

  def computeScore(n: Int):Int = n match {
    case 0 => 0
    case 1 => 40
    case 2 => 100
    case 3 => 300
    case 4 => 1200
  }

  def printGrid: Unit= {
    for (j<- 1 to _nbRows) {
      for (i<- 1 to _nbCols) {
        blocks (i) (j) match {
          case true =>
            if ((j*_nbCols+i)%10==0) println(". ")
            else print(". ")
          case false => 
            if ((j*_nbCols+i)%10==0) println("_ ")
            else print("_ ")
        }
      } 
    }
  }

}
