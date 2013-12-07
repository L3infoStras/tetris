package scalatris.server

import scalatris._

import swing._
import event.Key._
import scala.swing.event._

//import scala.swing.event._
import java.awt.{BasicStroke}
import java.awt.{Color => AWTColor}

import java.awt.geom._

class GridCanvas(val grid: TetrisGrid) extends Component {
  val nbCols = 10
  val nbRows = 20

  val bluishGray = new AWTColor(48, 99, 99)
  val bluishSilver = new AWTColor(210, 255, 255)
  val lightBluishGray = new AWTColor(204, 204, 178)

  override def paintComponent(g : Graphics2D) {
    val cellSize = ((size.height*0.9) / nbRows).toInt
    val x0 = 0
    val y0 = 0

    def fillCell(x:Int, y:Int) {
      g.setColor(lightBluishGray)
      g.fillRect(x0 + x*cellSize, y0 + y*cellSize, cellSize, cellSize)
    }

    def drawVerticalLines {
      g.setColor(bluishSilver)
      for (x <- 0 to nbCols)
        g.draw(new Line2D.Double(x0 + x * cellSize, y0, x0 + x * cellSize, y0 + cellSize*nbRows))
    }

    def drawHorizontalLines {
      g.setColor(bluishSilver)
      for (y <- 0 to nbRows)
        g.draw(new Line2D.Double(x0, y0 + y * cellSize, x0 + cellSize*nbCols, y0 + y * cellSize))
    }




    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
		       java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(lightBluishGray)
    g.fillRect(0, 0, size.width, size.height)
    g.setColor(bluishGray)
    g.fillRect(0, 0, nbCols*cellSize, nbRows*cellSize)

    fillCell(4, 5)
    fillCell(4, 6)
    fillCell(4, 7)
    fillCell(5, 7)



    drawVerticalLines
    drawHorizontalLines

    g.setStroke(new BasicStroke(3f))


  }
}

object ScalatrisGui extends SimpleSwingApplication {

  val grid = new TetrisGrid(10, 20)

  val canvas = new GridCanvas(grid)

  val label =  new Label {
      text = "No click yet"
  }

  def top = new MainFrame {
    title = "Scalatris"
    contents = mainPanel
    }

  def onKeyPress(keyCode: Value) = keyCode match {
    case Left | H => label.text = "LEFT"
    case Right | L => label.text = "RIGHT"
    case Down | J => label.text = "DOWN"
    case Space => label.text = "SPACE"
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
