package scalatris

import org.scalatest._
import prop._

class TetrisGridSpec extends PropSpec with Matchers {
  val grid =  new TetrisGrid(10,20)

  property("a grid's size should be nbCol*nbRaw") {
    grid.nbCase should be (200)
  }
}
