package scalatris

import Array._
import scala.util._

class AI (tg:TetrisGrid) {
  val left = 'q'
  val right = 'd'
  val bot = 's'
  val rot = 'z'

  // bon le code c'est un peu de la merde mais il faudrait rajouter des constructeurs dans AIgrid pour 
  // clarifier. Mais l'algo devrait marcher.
  def listGrid (ag:AIgrid) (preInput:List[Char]): List[(AIgrid, List[Char])] = {
    def reachBot (aig:AIgrid, mvl:List[Char]): (AIgrid,List[Char]) = {
      if(aig.possibleMvDown) 
        reachBot (new AIgrid(aig.shapeAbs-1,aig.shapeOrd,aig.curShape,aig.grid),
                 mvl :+ bot)
      else {
        aig.draw
        (aig,preInput++mvl)
      }
    }
    def reachLeft (aig:AIgrid, mvl:List[Char]): List[(AIgrid,List[Char])] = {
      if(aig.possibleMvLeft)
        reachLeft (new AIgrid(aig.shapeAbs,aig.shapeOrd-1,aig.curShape,aig.grid),
                  left::mvl) :+ reachBot (aig, mvl)
      else Nil
    }
    def reachRight (aig:AIgrid, mvl:List[Char]): List[(AIgrid,List[Char])] = {
      if(aig.possibleMvLeft)
        reachRight (new AIgrid(aig.shapeAbs,aig.shapeOrd+1,aig.curShape,aig.grid),
                   right::mvl) :+ reachBot (aig, mvl)
      else Nil
    }
    reachLeft (new AIgrid (ag.shapeAbs, ag.shapeOrd-1, ag.curShape, ag.grid),List(left)) ++ reachRight (ag,Nil)
  }

  def listWithRot (ag:AIgrid): List[(AIgrid, List[Char])] = {
    def listRot (aig:AIgrid) (shStop:Shape) (preInput:List[Char]): List[(AIgrid, List[Char])] = {
      if(aig.curShape!=shStop) 
        listGrid (new AIgrid(aig.shapeAbs-1, aig.shapeOrd, aig.curShape.rotation, aig.grid)) (preInput :+ rot)
      else Nil
    }
    listGrid (ag) (Nil) ++ 
    listRot (new AIgrid(ag.shapeAbs-1, ag.shapeOrd, ag.curShape.rotation, ag.grid)) (ag.curShape) (List(bot,rot))
  }

  def computeAI: List[Char] = {
    def choseVal (v1:(Double,List[Char]),v2:(Double,List[Char])): (Double,List[Char]) = {
      if (v1._1>v2._1) v1
      else v2
    }
    val gridList = listWithRot (new AIgrid(tg.shapeAbs-1, tg.shapeOrd, tg.curShape.rotation, tg.grid)) 
    ((gridList map (v => (v._1.eval,v._2))) reduceLeft (choseVal (_,_)))._2
  }
}
