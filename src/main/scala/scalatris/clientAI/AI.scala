package scalatris.clientAI

import scalatris.lib._
import Array._
import scala.util._

object AI {
  // Déplace la forme jusqu'en bas de la grille
  def fallBot (aigrid: AIgrid): AIgrid = { 
    aigrid.fall
    aigrid
   }

  // Déplace la forme vers la droite et appelle reachBot a chaque appel 
  // récursif
  // Des préconditions ca serait super la-dessus (seul dir=DirLeft | DirRight)
  def reach (aigrid: AIgrid) (dir:Direction): List[AIgrid] = {
    fallBot(new AIgrid(aigrid)) ::
    (if(aigrid.moveIsPossible(dir)) 
       reach (new AIgrid(aigrid,Some(dir))) (dir)
    else Nil)
  }

  // Génére une liste de grille pour seulement un type de rotation
  def listGrid (aigrid:AIgrid): List[AIgrid] = {
    reach (new AIgrid (aigrid,Some(DirLeft))) (DirLeft) ++ 
    reach (new AIgrid (aigrid)) (DirRight)
  }

  // Génére une liste de grille cette fois avec les rotations
  def listGridWithRot (aigrid:AIgrid): List[AIgrid] = {
    // appel listGrid sur les différentes rotations possibles de la forme
    // actuelle
    def listRot (aig:AIgrid) (shapeStop:Shape): List [AIgrid] = {
      if(!shapeStop.equals(aig.shape) && aig.rotationIsPossible)
        listGrid(aig) ++ listRot(new AIgrid (aig,Some(Rotation))) (shapeStop) 
      else 
        Nil
    }
    // on concatène toutes les listes ensembles
    listGrid (aigrid) ++ 
    (if(aigrid.rotationIsPossible) 
      listRot (new AIgrid(aigrid, Some(Rotation))) (aigrid.shape)
    else Nil)
  }

  // calcule l'ia et renvoit la grille avec le meilleur score
  def computeAI (aigrid:AIgrid): AIgrid = {
    // De deux grilles renvoit celle dont le score est supérieur a l'autre
    def chooseGrid (g:AIgrid,h:AIgrid): AIgrid = {
      if (g.eval > h.eval) g
      else h
    }
    // reduceLeft sur la liste avec la fonction choseGrid
    (listGridWithRot (aigrid)) reduceLeft (chooseGrid (_,_))
  }
}
