package scalatris.lib

class Shape(pos: (Int, Int), cellList: List[(Int, Int)]) {
  var (x, y) = pos
  var cells = cellList

  def rotate = {
    cells = cells
  }


  def makeMove (dir: Direction) {
    x = x + dir.x
    y = y + dir.y
  }
}
