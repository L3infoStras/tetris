package scalatris.server.ui

import scalatris.lib._

import swing._

import java.awt.{Color => AWTColor}

import java.awt.geom.Line2D


class GridCanvas(val grid: TetrisGrid) extends Component {

  // on définit quelques couleurs...
  val lightBlack = new AWTColor(5, 5, 5)
  val bluishGray = new AWTColor(48, 99, 99)
  val bluishSilver = new AWTColor(210, 255, 255)
  val lightBluishGray = new AWTColor(204, 204, 178)

  override def paintComponent(g : Graphics2D) {
    val cellSize = ((size.height*0.9) / grid.nbRows).toInt
    val x0 = 0
    val y0 = 0

    def fillCell(x:Int, y:Int) {
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
      g.setColor(s.color)
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




    g.setColor(lightBluishGray)
    for (i <- 0 until grid.nbCols) {
      for (j <- 0 until grid.nbRows) {
        if (grid.blocks(i)(j)) {
          fillCell(i, j)
        }
      }
    }


    drawVerticalLines
    drawHorizontalLines

    // une ligne noire pour cacher la ligne avant la grille
    g.setColor(lightBlack)
    g.fillRect(0, 0, size.width, cellSize)
  }
}
