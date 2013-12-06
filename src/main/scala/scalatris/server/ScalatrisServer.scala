package scalatris.server

import scalatris._

import swing._

import scala.swing.event._
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

    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
		       java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(lightBluishGray)
    g.fillRect(0, 0, size.width, size.height)
    g.setColor(bluishGray)
    g.fillRect(0, 0, nbCols*cellSize, nbRows*cellSize)

    g.setColor(bluishSilver)
    // vertical lines
    for (x <- 0 to nbCols)
      g.draw(new Line2D.Double(x0 + x * cellSize, y0, x0 + x * cellSize, y0 + cellSize*nbRows))
    // horizontal lines
    for (y <- 0 to nbRows)
      g.draw(new Line2D.Double(x0, y0 + y * cellSize, x0 + cellSize*nbCols, y0 + y * cellSize))
    g.setStroke(new BasicStroke(3f))

  }
}

object ScalatrisGui extends SimpleSwingApplication {

  val grid = new TetrisGrid(10, 20)

  val canvas = new GridCanvas(grid)

  def top = new MainFrame {
    title = "Scalatris"
    contents = mainPanel
    }

  def mainPanel = new BoxPanel(Orientation.Horizontal) {
    preferredSize = new Dimension(640, 480)
    contents += canvas
  }
}
