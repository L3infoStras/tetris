package scalatris.clientAI

import scalatris.lib._
import Array._
import scala.util._

// on génère désormais la liste des entrées dans AIgrid
// mvList s'utilise pour remettre la liste a zero
class AIgrid (grid:TetrisGrid,dir:Option[Direction]) extends TetrisGrid(10,20) {
  //init
  dir match {
    case Some(dir) => shape = grid.shape.makeMove(dir) 
    case None => shape = grid.shape
  }
  blocks = ofDim[Boolean](nbCols,nbRows)
  for {
    i <- 0 until nbCols
    j <- 0 until nbRows
  } blocks (i) (j) =  grid.blocks (i) (j) // on copie l'array var 
  //fin init
 
  // memoire des entrées
  moveList = {
    dir match { 
      case Some(dir) => grid.moveList :+ lastMove(dir)
      case None => grid.moveList  
    }
  }

  def printList: Unit = 
    moveList map (println(_))
   
  // coefficient de la fonction eval
  val coefHole: Double = -4
  val coefClear: Double = 2
  val coefHeight: Double = -3
  val coefBlockade: Double = -2
  val bonusFloor: Double = 3
  val bonusEdge: Double = 1
  val bonusSide: Double = 2

  // fonction de sélection des entrées
  def lastMove (d:Direction): String = d.toString

  // constructeur simple
  def this (grid:TetrisGrid) = {
    this(grid,None)
  }

  // fonction de bonus pour les blocks
  def bonusBlock (i:Int) (j:Int): Double = {
    def bonus: Double = {
      val neighbours: List[(Int,Int)] = List((i-1,j),(i+1,j),(i,j-1),(i,j+1))
      (neighbours filter (x => x._1!=0 || x._1!=11 || x._2!=0)) map 
        (t => if (blocks (t._1) (t._2)) bonusEdge else 0) reduceLeft (_+_)
    }
    if(i==1 || i==10) {
      if(j==20) bonus + bonusFloor + bonusSide
      else bonus + bonusSide
    }
    else bonus
  }

  // Evalue la grille et renvoit un score
  def eval: Double = {
    def evalAux (abs:Int,ord:Int) (nbHoles:Int,nbBlockades:Int,nbClears:Int,normal:Int) (acc:Double): Double = {
      if (ord<=20) {
        if (blocks (abs) (ord)) {
          val blockVal = acc + (20-ord) * coefHeight + bonusBlock(abs)(ord)
          evalAux (abs,ord+1) (nbHoles,nbBlockades,nbClears,normal+1) (blockVal)
        }
        else {
          if (normal>0 || nbBlockades>0) 
            evalAux (abs,ord+1) (nbHoles+1,normal+nbBlockades,nbClears,0) (acc)
          else 
            evalAux (abs,ord+1) (nbHoles,nbBlockades,nbClears+1,normal) (acc)
        }
      }
      else acc + nbHoles*coefHole + nbBlockades*coefBlockade+nbClears*coefClear
    }
    ((1 to 10) map (j => evalAux (j,1) (0,0,0,0) (0))) reduceLeft (_+_)
  }
}
