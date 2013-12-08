package scalatris

object Main {
    def main(args: Array[String]) {
      val g = new TetrisGrid
      println("Abscisse:" + g.shapeAbs)
      println("Ordonnee:" + g.shapeOrd)
      for (i <- 0 until g.nbCol-1) yield g.setCase(19,i) (true)
      for (i <- 2 until g.nbCol) yield g.setCase(18,i) (true)
      for (i <- 1 until g.nbCol-5) yield g.setCase(17,i) (true)
      for (i <- 0 until 2) yield g.setCase(16,i) (true)
      var input: Char = Console.readChar
      val aig = new AIgrid(4,5,g.curShape,g.grid)
      val ai = new AI(aig)
      println(ai.computeAI)
      g.printGrid
      println(aig.eval)
      while(input!='r')
      {
        input=Console.readChar
        if(input=='d') g.mvShapeRight
        if(input=='q') g.mvShapeLeft
        if(input=='s') g.mvShapeDown
        if(input=='z') g.rotateShape
        g.printGrid
        println(aig.eval)
      }
    }
}

