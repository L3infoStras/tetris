import scalatris.lib._
import scalatris.clientAI._

object AItestShell {
    def main(args: Array[String]) {
      val g = new TetrisGrid(10,20)
      var aig = new AIgrid(g)
      var input = ' '
      println("appuyer sur q pour quitter, n'importe quelle autre touche pour tester l'ia puis appuyer sur entrée")
      //for (i <- 2 until g.nbCols-3)  g.blocks (i) (20)=true
      //for (i <- 0 until g.nbCols-1)  g.blocks (i) (19)=true
      //for (i <- 2 until g.nbCols) g.blocks (i) (18)=true
      //for (i <- 1 until g.nbCols-5) g.blocks (i) (17)=true
      //for (i <- 0 until 2) g.blocks (i) (16)=true
      while(input!='q')
      { 
        aig = AI.computeAI (new AIgrid(aig))
        aig.printGrid ; println(aig.eval) ; println (aig.moveList) ; 
        // /!\ il faut remettre moveList a zero manuellement sinon la liste grossie artificiellement comme son intelligence
        aig.moveList=Nil
        input = Console.readChar()
      }
      //val aigList: List[AIgrid] = AI.listGridWithRot(new AIgrid(g))  
      //aigList map (g => { g.printGrid ; println(g.eval) ; println (g.moveList)} ) 
    }
}

