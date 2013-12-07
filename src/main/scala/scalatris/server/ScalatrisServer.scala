package scalatris.server

import scalatris._

import swing._
import event.Key._
import scala.swing.event._

import Array.ofDim

//import scala.swing.event._
import java.awt.{BasicStroke}
import java.awt.{Color => AWTColor}

import java.awt.geom._

sealed abstract class Direction {
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


class Shape(pos: (Int, Int), cellList: List[(Int, Int)]) {
  var (x, y) = pos
  var cells = cellList

  def rotate = {
    cells = cells
  }


  def makeMove (dir: Direction) {
    x = x + dir.x
    y = y + dir.y
  }
}


class GridCanvas(val grid: Grid) extends Component {
  val lightBlack = new AWTColor(5, 5, 5)
  val bluishGray = new AWTColor(48, 99, 99)
  val bluishSilver = new AWTColor(210, 255, 255)
  val lightBluishGray = new AWTColor(204, 204, 178)


  override def paintComponent(g : Graphics2D) {
    val cellSize = ((size.height*0.9) / grid.nbRows).toInt
    val x0 = 0
    val y0 = 0

    def fillCell(x:Int, y:Int) {
      g.setColor(lightBluishGray)
      g.fillRect(x0 + x*cellSize, y0 + y*cellSize, cellSize, cellSize)
    }
  
    def drawVerticalLines {
      g.setColor(bluishSilver)
      for (x <- 0 to grid.nbCols)
        g.draw(new Line2D.Double(x0 + x * cellSize, y0, x0 + x * cellSize, y0 + cellSize*grid.nbRows))
    }

    def drawHorizontalLines {
      g.setColor(bluishSilver)
      for (y <- 0 to grid.nbRows)
        g.draw(new Line2D.Double(x0, y0 + y * cellSize, x0 + cellSize*(grid.nbCols), y0 + y * cellSize))
    }

    def drawShape (s: Shape) {
      for (c <- s.cells) {
        fillCell(s.x + c._1, s.y + c._2)
      }
    }

    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
		       java.awt.RenderingHints.VALUE_ANTIALIAS_ON)

    // on remplit tout de noir
    g.setColor(lightBlack)
    g.fillRect(0, 0, size.width, size.height)

    // on remplit la zone de jeu en gris
    g.setColor(lightBluishGray)
    g.fillRect(0, cellSize, (grid.nbCols+1)*cellSize, (grid.nbRows+1)*cellSize)

    // on remplit la zone où se déplacent les pieces, de bleu
    g.setColor(bluishGray)
    g.fillRect(cellSize, cellSize, grid.nbCols*cellSize, grid.nbRows*cellSize)


    drawShape(grid.shape)

    for (i <- 0 until grid.nbCols) {
      for (j <- 0 until grid.nbRows) {
        if (grid.blocks(i)(j)) {
          fillCell(i, j)
        }
      }
    }


    drawVerticalLines
    drawHorizontalLines

    g.setStroke(new BasicStroke(3f))


  }
}

object ScalatrisGui extends SimpleSwingApplication {

  val grid = new Grid(10, 20)

  val canvas = new GridCanvas(grid)

  val label =  new Label {
      text = "No click yet"
  }

  def top = new MainFrame {
    title = "Scalatris"
    contents = mainPanel
    }

  def onKeyPress(keyCode: Value) = keyCode match {
    case Left | H => grid.move(DirLeft)
    case Right | L => grid.move(DirRight)
    case Down | J => grid.move(DirDown)
    case Up | R | K => grid.rotate
    case Space => grid.fall
    case Escape | Q => quit
    case _ => println("Unknown key " ++ keyCode.toString)
  }


  def mainPanel = new BoxPanel(Orientation.Horizontal) {


    preferredSize = new Dimension(640, 480)
    contents += canvas
    contents += label

    focusable = true
    requestFocus

    listenTo(keys)
    reactions += {
      case KeyPressed(_, key, _, _) =>
        onKeyPress(key)
        repaint
    }
  }
}
