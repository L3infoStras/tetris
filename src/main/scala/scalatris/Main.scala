import scalatris.lib._
import scalatris.clientAI._

object AItestShell {
    def main(args: Array[String]) {
      val g = new TetrisGrid(10,20)
      var aig = new AIgrid(g)
      var input = ' '

      while(!aig.gameLost)
      { 
        aig = AI.computeAI (new AIgrid(aig))
        aig.printGrid
        println(aig.eval)
        println (aig.moveList) ;

        // /!\ il faut remettre moveList a zero manuellement sinon la liste grossie artificiellement comme son intelligence
        aig.moveList=Nil
        //input = Console.readChar()
        java.lang.Thread.sleep(1000)
      }
      //val aigList: List[AIgrid] = AI.listGridWithRot(new AIgrid(g))  
      //aigList map (g => { g.printGrid ; println(g.eval) ; println (g.moveList)} ) 
    }
}

