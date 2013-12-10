import scalatris.lib._
import scalatris.clientAI._

object Main {
    def main(args: Array[String]) {
      val g = new TetrisGrid(10,20)
      for (i <- 2 until g.nbCols-3)  g.blocks (i) (20)=true
      for (i <- 0 until g.nbCols-1)  g.blocks (i) (19)=true
      for (i <- 2 until g.nbCols) g.blocks (i) (18)=true
      for (i <- 1 until g.nbCols-5) g.blocks (i) (17)=true
      for (i <- 0 until 2) g.blocks (i) (16)=true
//      var input: Char = Console.readChar
      //g.blocks (10) (1) = true
      val aigList: List[AIgrid] = AI.listGridWithRot(new AIgrid(g))  
/*      val aig2 = new AIgrid(new AIgrid(aig,DirLeft))  
      val aig3 = new AIgrid(new AIgrid(aig2,DirLeft))  
      val aig4 = new AIgrid(new AIgrid(aig3,DirLeft))  
      aig.fall
      aig.printGrid
      aig2.fall
      aig2.printGrid
      aig3.fall
      aig3.printGrid
      aig4.fall
      aig4.printGrid */
      aigList map (g => { g.printGrid ; println(g.eval) ; println (g.moveList)} ) 
      val aig: AIgrid = AI.computeAI (new AIgrid(g))
      aig.printGrid ; println(aig.eval) ; println (aig.moveList) ;
      //println(aig.eval)
      //println(AI.computeAI(aig))
/*      while(input!='r')
      {
        input=Console.readChar
        if(input=='d') g.mvShapeRight
        if(input=='q') g.mvShapeLeft
        if(input=='s') g.mvShapeDown
        if(input=='z') g.rotateShape
        println(g.collision)
        g.printGrid
      }
     println(g.collision)
      g.draw 
      g.printGrid
      println(g.collision)
      g.mvShapeRight
      g.mvShapeRight
      g.mvShapeRight
      g.mvShapeRight
      g.mvShapeRight
      println(g.collision)
      g.printGrid
      g.mvShapeLeft
      println(g.collision)
      g.printGrid
      g.mvShapeRight
      println(g.collision)
      g.printGrid
*/
    }
}

