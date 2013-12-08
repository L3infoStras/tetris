package scalatris

import Array._
import scala.util._

class AIgrid (abs:Int, ord:Int, s:Shape, g:Array[Boolean]) extends TetrisGrid {
  override val grid = g
  curShape = s
  shapeAbs = abs
  shapeOrd = ord

  val coefHole: Double = -2.31
  val coefClear: Double = 1.6
  val coefHeight: Double = -3.78
  val coefBlockade: Double = -0.59

  // évalue la grille et lui attribue un score 
  def eval: Double = {
    def evalAux (abs:Int,ord:Int) (nbHoles:Int,nbBlockades:Int,nbClears:Int,normal:Int) (acc:Double): Double = {
      if (abs<20) {
        if (getCase(abs,ord)) 
          evalAux (abs+1,ord) (nbHoles,nbBlockades,nbClears,normal+1) (acc+(20-abs)*coefHeight)
        else {
          if (normal>0 || nbBlockades>0) evalAux (abs+1,ord) (nbHoles+1,normal+nbBlockades,nbClears,0) (acc)
          else evalAux (abs+1,ord) (nbHoles,nbBlockades,nbClears+1,normal) (acc)
        }
      }
      else acc+nbHoles*coefHole+nbBlockades*coefBlockade+nbClears*coefClear
    }
    ((0 until nbCol) map (j => evalAux (0,j) (0,0,0,0) (0))) reduceLeft (_+_)
  }

  def possibleMvLeft: Boolean = {
    shapeOrd = shapeOrd-1
    if (collision) {
      shapeOrd = shapeOrd+1
      false
    }
    else {
      shapeOrd = shapeOrd+1
      true
    }
  }

  def possibleMvRight: Boolean = {
    shapeOrd = shapeOrd+1
    if (collision) {
      shapeOrd = shapeOrd-1
      false
    }
    else {
      shapeOrd = shapeOrd-1
      true
    }
  }

  def possibleMvDown: Boolean = {
    shapeAbs = shapeAbs+1
    if (collision) {
      shapeAbs = shapeAbs-1
      false
    }
    else {
      shapeAbs = shapeAbs-1
      true
    }
  }
}
