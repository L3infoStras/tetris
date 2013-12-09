package scalatris.clientAI

import scalatris.lib._
import Array._
import scala.util._

class AI {
  val left = 'q'
  val right = 'd'
  val bot = 's'
  val rot = 'z'

  // Genere une liste de couple (grille, entrees) 
  // pour une ligne selon une rotation particulière
  def listGrid (ag:AIgrid) (preInput:List[Char]): List[(AIgrid, List[Char])] = {
    // Déplace la forme jusqu'en bas de la grille et enregistre les entrées
    // en meme temps
    def reachBot (aig:AIgrid, mvl:List[Char]): (AIgrid,List[Char]) = {
      if(aig.moveIsPossible(DirDown)) { 
        aig.move(DirDown)
        reachBot (aig,       // pas besoin de copier cette grille la
                 mvl :+ bot) // enfin je crois :)
      }
      else {
        aig.move(DirDown)
        (aig,preInput++mvl)
      }
    }
    // En partant de la forme actuelle genère une liste de forme vers la gauche
    // ainsi que les entrées associées aux déplacements
    def reachLeft (aig:AIgrid, mvl:List[Char]): List[(AIgrid,List[Char])] = {
      if(aig.moveIsPossible(DirLeft))
        reachLeft (new AIgrid(aig,DirLeft),
                  left::mvl) :+ reachBot (aig, mvl)
      else List(reachBot (aig, mvl))
    }
    // En partant de la forme actuelle genère une liste de forme vers la droite
    // ainsi que les entrées associées aux déplacements
    def reachRight (aig:AIgrid, mvl:List[Char]): List[(AIgrid,List[Char])] = {
      if(aig.moveIsPossible(DirRight))
        reachRight (new AIgrid(aig,DirRight),
                   right::mvl) :+ reachBot (aig, mvl)
      else List(reachBot (aig, mvl))
    }
    reachLeft (new AIgrid (ag,DirLeft),List(left)) ++ reachRight (ag,Nil)
  }

  // Genere la liste pour toutes les rotations de la forme actuelle
  def listWithRot (ag:AIgrid): List[(AIgrid, List[Char])] = {
    // parcours la liste des rotations possible jusqu'a revenir à la premiere
    def listRot (aig:AIgrid) (shStop:Shape) (preInput:List[Char]): List[(AIgrid, List[Char])] = {
      if(aig.shape!=shStop && aig.rotationIsPossible) {
        listGrid (aig) (preInput) ++ 
        listRot (new AIgrid(aig,Rotation)) (shStop) (preInput :+ rot)
      }
      else Nil
    }
    listGrid (ag) (Nil) ++ 
    listRot (new AIgrid(new AIgrid(ag,DirDown),Rotation)) (ag.shape) (List(bot,rot))
  }

  // Calcule la meilleure grille et renvoit la séquence d'entrées associées
  def computeAI (ag:AIgrid): List[Char] = {
    def choseVal (v1:(Double,List[Char]),v2:(Double,List[Char])): (Double,List[Char]) = {
      if (v1._1>v2._1) v1
      else v2
    }
    val gridList = listWithRot (ag) 
    ((gridList map (v => (v._1.eval,v._2))) reduceLeft (choseVal (_,_)))._2
  }
}
