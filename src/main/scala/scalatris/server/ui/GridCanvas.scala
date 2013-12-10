package scalatris.server.ui

import scalatris.lib._

import swing._

import java.awt.{Color => AWTColor}

import java.awt.geom.Line2D


class GridCanvas(val grid: TetrisGrid) extends Component {

  // on définit quelques couleurs...
  val white = new AWTColor(200, 200, 200)
  val red = new AWTColor(200, 40, 10)
  val lightBlack = new AWTColor(5, 5, 5)
//  val white = new AWTColor(250, 250, 250)
  val lightBluishGray = new AWTColor(204, 204, 178)
  val lightGray = new AWTColor(240, 240, 240)
  val gray = new AWTColor(179, 179, 179)

  override def paintComponent(g : Graphics2D) {
    val cellSize = ((size.height*0.9) / grid.nbRows).toInt
    val x0 = 0
    val y0 = 0

    def fillCell(x:Int, y:Int) {
      g.fill3DRect(x0 + x*cellSize, y0 + y*cellSize, cellSize, cellSize, true)
    }
  
    def drawVerticalLines {
      g.setColor(white)
      for (x <- 1 to grid.nbCols)
        g.draw(new Line2D.Double(x0 + x * cellSize, y0, x0 + x * cellSize, y0 + cellSize*grid.nbRows))
    }

    def drawHorizontalLines {
      g.setColor(white)
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

    // on remplit la zone où se déplacent les pieces
    g.setColor(lightGray)
    g.fillRect(cellSize, cellSize, (grid.nbCols - 2)*cellSize,
      (grid.nbRows - 1)*cellSize)

    // on remplit les bordures de la grille
    g.setColor(gray)
    for (i <- 1 until grid.nbRows) {
      fillCell(0, i)
      fillCell(grid.nbCols - 1, i)
    }

    for (i <- 0 until grid.nbCols)
      fillCell(i, grid.nbRows - 1)


    if (!grid.gameLost)
      drawShape(grid.shape)




    g.setColor(lightBluishGray)
    for (i <- 1 until grid.nbCols - 1) {
      for (j <- 1 until grid.nbRows -1) {
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


    g.setColor(white)
    g.drawString("Score: " ++ grid.score.toString,
      (grid.nbCols + 2)*cellSize, 5*cellSize)

    g.setColor(red)
    if (grid.gameLost) {
      g.drawString("Vous avez perdu.",
        (grid.nbCols + 1)*cellSize, 7*cellSize)
      g.drawString("Quittez le jeu avec 'q'.",
        (grid.nbCols + 1)*cellSize, 8*cellSize)
    }

  }
}
