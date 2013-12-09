package scalatris.clientAI

import scalatris.lib._
import Array._
import scala.util._


class AIgrid (tetGrid: TetrisGrid, dir: Direction) extends TetrisGrid(10, 20) {
  shape = tetGrid.shape.makeMove(dir) 
  blocks = blocks.map(_.clone) // copie profonde de la matrice

  val coefHole: Double = -2.31
  val coefClear: Double = 1.6
  val coefHeight: Double = -3.78
  val coefBlockade: Double = -0.59

  def this (tetGrid:TetrisGrid) = {
    this(tetGrid, NoMove)
  }

  // Evalue la grille et renvoit un score
  def eval: Double = {
    def evalAux (x:Int, y:Int) (nbHoles: Int, nbBlockades: Int, nbClears: Int, normal:Int) (acc:Double): Double = {
      if (y <= nbRows - 1) {
        if (blocks(x)(y)) 
          evalAux (x, y + 1) (nbHoles, nbBlockades, nbClears, normal + 1) (acc + (20 - x)*coefHeight)
        else {
          if (normal > 0 || nbBlockades > 0) 
            evalAux (x, y + 1) (nbHoles + 1,normal + nbBlockades, nbClears, 0) (acc)
          else evalAux (x, y + 1) (nbHoles, nbBlockades, nbClears + 1, normal) (acc)
        }
      }
      else acc + nbHoles*coefHole + nbBlockades*coefBlockade+nbClears*coefClear
    }
    ((1 until nbCols) map (j => evalAux (1,j) (0, 0, 0, 0) (0))) reduceLeft (_+_)
  }
}
