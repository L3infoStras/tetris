package scalatris

import Array._
import scala.collection.immutable.Stack._
import scala.util._

class AIgrid (x:Double, y:Double, z:Double, t:Double, g:Array[Boolean]) extends TetrisGrid {
  override val grid = g
  val coefHole = x
  val coefClear = y
  val coefHeight = z
  val coefBlockade = t

  def eval: Double = {
    def evalAux (abs:Int,ord:Int) (nbHoles:Int,nbBlockades:Int,nbClear:Int,normal:Int) (acc:Double): Double = {
      if (abs>0) {
        if (getCase(abs,ord)) 
          evalAux (abs-1,ord) (nbHoles,nbBlockades,nbClear,normal+1) (acc+(20-abs)*coefHeight)
        else {
          if (normal>0 || nbBlockades>0) evalAux (abs-1,ord) (nbHoles+1,normal+nbBlockades,nbClear,0) (acc)
          else evalAux (abs-1,ord) (nbHoles,nbBlockades,nbClear+1,normal) (acc)
        }
      }
      else acc+nbHoles*coefHole+nbBlockades*coefBlockade+nbClear*coefClear
    }
    ((0 until nbCol) map (j => evalAux (0,j) (0,0,0,0) (0))) reduceLeft (_+_)
  }
}
