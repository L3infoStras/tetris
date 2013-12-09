package scalatris.clientAI

import scalatris.lib._
import Array._
import scala.util._


class AIgrid (tetGrid:TetrisGrid,dir:Direction) extends TetrisGrid(10,20) {
  shape = tetGrid.shape.makeMove(dir) 
  blocks = tetGrid.blocks.clone // on copie l'array 

  val coefHole: Double = -2.31
  val coefClear: Double = 1.6
  val coefHeight: Double = -3.78
  val coefBlockade: Double = -0.59

  def this (tetGrid:TetrisGrid) = {
    this(tetGrid,NoMove)
  }

  // Evalue la grille et renvoit un score
  def eval: Double = {
    def evalAux (abs:Int,ord:Int) (nbHoles:Int,nbBlockades:Int,nbClears:Int,normal:Int) (acc:Double): Double = {
      if (ord<=20) {
        if (blocks (abs) (ord)) 
          evalAux (abs,ord+1) (nbHoles,nbBlockades,nbClears,normal+1) (acc+(20-ord)*coefHeight)
        else {
          if (normal>0 || nbBlockades>0) 
            evalAux (abs,ord+1) (nbHoles+1,normal+nbBlockades,nbClears,0) (acc)
          else evalAux (abs,ord+1) (nbHoles,nbBlockades,nbClears+1,normal) (acc)
        }
      }
      else acc+nbHoles*coefHole+nbBlockades*coefBlockade+nbClears*coefClear
    }
    ((1 to 10) map (j => evalAux (j,1) (0,0,0,0) (0))) reduceLeft (_+_)
  }
}



