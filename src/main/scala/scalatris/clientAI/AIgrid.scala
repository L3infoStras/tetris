package scalatris.clientAI

import scalatris.lib._
import Array._
import scala.util._

// on génère désormais la liste des entrées dans AIgrid
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
  override val moveList: List[Char] = {
    dir match { 
      case Some(dir) => grid.moveList :+ lastMove(dir)
      case None => grid.moveList
    }
  }
   
  // caractères d'entrée
  val left = 'q'
  val right = 'd'
  val bot = 's'
  val rot = 'z'

  // coefficient de la fonction eval
  val coefHole: Double = -2.31
  val coefClear: Double = 1.6
  val coefHeight: Double = -3.78
  val coefBlockade: Double = -0.59

  // fonction de sélection des entrées
  def lastMove (d:Direction): Char = {
    d match {
      case DirRight => right
      case DirLeft => left
      case DirDown => bot
      case Rotation => rot
    }
  }

  // constructeur simple
  def this (grid:TetrisGrid) = {
    this(grid,None)
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
