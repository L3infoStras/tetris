package clientAI

import scalatris.lib
import Array._
import scala.util._


class AIgrid (tetGrid:TetrisGrid) extends TetrisGrid(_nbCols,_nbRaws) {
  shape = tetGrid.shape 
  blocks = blocks.clone // on copie l'array 

  val coefHole: Double = -2.31
  val coefClear: Double = 1.6
  val coefHeight: Double = -3.78
  val coefBlockade: Double = -0.59

  /*def this (tetGrid:TetrisGrid, dir:Direction) = {
    shape = tetGrid.shape.makeMove(Dir) 
    blocks = blocks.clone // on copie l'array 
  }*/

  // Evalue la grille et renvoit un score
  def eval: Double = {
    def evalAux (abs:Int,ord:Int) (nbHoles:Int,nbBlockades:Int,nbClears:Int,normal:Int) (acc:Double): Double = {
      if (abs<20) {
        if (getCase(abs,ord)) 
          evalAux (abs+1,ord) (nbHoles,nbBlockades,nbClears,normal+1) (acc+(20-abs)*coefHeight)
        else {
          if (normal>0 || nbBlockades>0) evalAux (abs+1,ord)
            (nbHoles+1,normal+nbBlockades,nbClears,0) (acc)
          else evalAux (abs+1,ord) (nbHoles,nbBlockades,nbClears+1,normal) (acc)
        }
      }
      else
        acc+nbHoles*coefHole+nbBlockades*coefBlockade+nbClears*coefClear
    }
    ((0 until nbCol) map (j => evalAux (0,j) (0,0,0,0) (0))) reduceLeft (_+_)
  }
}



