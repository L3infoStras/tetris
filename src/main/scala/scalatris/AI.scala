package scalatris

import Array._
import scala.util._

class AI (tg:TetrisGrid) {
  val left = 'q'
  val right = 'd'
  val bot = 's'
  def listGrid: List[(AIgrid, List[Char])] = {
    def reachBot (aig:AIgrid, mvl:List[Char]): (AIgrid,List[Char]) = {
      if(aig.possibleMvDown) 
        reachBot (new AIgrid(aig.shapeAbs-1,aig.shapeOrd,aig.curShape,aig.grid),
                 mvl :+ bot)
      else (aig,mvl)
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
    val feedLeft = new AIgrid (tg.shapeAbs, tg.shapeOrd-1, tg.curShape, tg.grid)
    val feedRight = new AIgrid (tg.shapeAbs, tg.shapeOrd, tg.curShape, tg.grid)
    reachLeft (feedLeft,Nil) ++ reachRight (feedRight,Nil)
  }
}
