package scalatris.server

import scalatris._

import swing._
import event.Key._
import scala.swing.event._


//import scala.swing.event._
import java.awt.{BasicStroke}
import java.awt.{Color => AWTColor}

import java.awt.geom._

import scalatris.lib._

class GridCanvas(val grid: Grid) extends Component {
  val lightBlack = new AWTColor(5, 5, 5)
  val bluishGray = new AWTColor(48, 99, 99)
  val bluishSilver = new AWTColor(210, 255, 255)
  val lightBluishGray = new AWTColor(204, 204, 178)
  val green = new AWTColor(30, 224, 40)


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
      for (x <- 1 to grid.nbCols)
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
    g.fillRect(0, cellSize, (grid.nbCols)*cellSize,
      (grid.nbRows - 1)*cellSize)

    // on remplit la zone où se déplacent les pieces, de bleu
    g.setColor(bluishGray)
    g.fillRect(cellSize, cellSize, (grid.nbCols - 2)*cellSize,
      (grid.nbRows - 1)*cellSize)


    
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

    g.setColor(lightBlack)
    g.fillRect(0, 0, size.width, cellSize)
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