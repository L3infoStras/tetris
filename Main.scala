object Main {
    def main(args: Array[String]) {
      val g = new TetrisGrid(10,20)
      //val s = Oshape0
      //println(s.grid)
      //println(s.getCase(1,2))
      //println(s.getCase(3,2))
      println("Abscisse:" + g.shapeAbs)
      println("Ordonnee:" + g.shapeOrd)
      g.drawIn (true)
      g.printGrid
      g.drawIn (false)
      g.printGrid
    }
}

