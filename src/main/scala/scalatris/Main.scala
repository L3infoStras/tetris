package scalatris

object Main {
    def main(args: Array[String]) {
      val g = new TetrisGrid(10,20)
      println("Abscisse:" + g.shapeAbs)
      println("Ordonnee:" + g.shapeOrd)
      g.setCase(4,7) (true)
      g.setCase(14,6) (true)
      g.setCase(15,5) (true)
      var input: Char = Console.readChar
      println(g.collision)
      g.printGrid
      while(input!='r')
      {
        input=Console.readChar
        if(input=='d') g.mvShapeRight
        if(input=='q') g.mvShapeLeft
        if(input=='s') g.mvShapeDown
        if(input=='z') g.rotateShape
        println(g.collision)
        g.printGrid
      }
 /*     println(g.collision)
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

