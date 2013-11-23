import Array._

trait Shape {
  val grid: Array[Boolean]  
  val dim = 4
  def rotation :Shape
  def getCase (i:Int,j:Int): Boolean= 
    grid.apply(i*dim+j)
}

// Forme T
object Tshape0 extends Shape {
  val grid=Array (false, false, false, false,
              false, true, true, true,
              false, false, true, false,
              false, false, false, false)
  def rotation = Tshape1
}

object Tshape1 extends Shape {
  val grid=Array (false, false, true, false,
              false, true, true, false,
              false, false, true, false,
              false, false, false, false)
  def rotation = Tshape2
}

object Tshape2 extends Shape {
  val grid=Array (false, false, true, false,
              false, true, true, true,
              false, false, false, false,
              false, false, false, false)
  def rotation = Tshape3
}

object Tshape3 extends Shape {
  val grid=Array (false, false, true, false,
              false, false, true, true,
              false, false, true, false,
              false, false, false, false)
  def rotation = Tshape0
}

// Forme O
object Oshape0 extends Shape {
  val grid=Array (false, false, false, false,
              false, true, true, false,
              false, true, true, false,
              false, false, false, false)
  def rotation = Oshape0
}

// Forme I
object Ishape0 extends Shape {
  val grid=Array (false, false, false, false,
              true, true, true, true,
              false, false, false, false,
              false, false, false, false)
  def rotation = Ishape1
}

object Ishape1 extends Shape {
  val grid=Array (false, false, true, false,
              false, false, true, false,
              false, false, true, false,
              false, false, true, false)
  def rotation = Ishape0
}

// Forme S
object Sshape0 extends Shape {
  val grid=Array (false, false, false, false,
              false, false, true, true,
              false, true, true, false,
              false, false, false, false)
  def rotation = Sshape1
}

object Sshape1 extends Shape {
  val grid=Array (false, true, false, false,
              false, true, true, false,
              false, false, true, false,
              false, false, false, false)
  def rotation = Sshape2
}

object Sshape2 extends Shape {
  val grid=Array (false, false, true, true,
              false, true, true, false,
              false, false, false, false,
              false, false, false, false)
  def rotation = Sshape3
}

object Sshape3 extends Shape {
  val grid=Array (false, false, true, false,
              false, false, true, true,
              false, false, false, true,
              false, false, false, false)
  def rotation = Sshape0
}

// Forme Z
object Zshape0 extends Shape {
  val grid=Array (false, false, false, false,
              false, true, true, false,
              false, false, true, true,
              false, false, false, false)
  def rotation = Zshape1
}

object Zshape1 extends Shape {
  val grid=Array (false, false, true, false,
              false, true, true, false,
              false, true, false, false,
              false, false, false, false)
  def rotation = Zshape2
}

object Zshape2 extends Shape {
  val grid=Array (false, true, true, false,
              false, false, true, true,
              false, false, false, false,
              false, false, false, false)
  def rotation = Zshape3
}

object Zshape3 extends Shape {
  val grid=Array (false, false, false, true,
              false, false, true, true,
              false, false, true, false,
              false, false, false, false)
  def rotation = Zshape0
}

// Forme L
object Lshape0 extends Shape {
  val grid=Array (false, false, false, false,
              false, true, true, true,
              false, true, false, false,
              false, false, false, false)
  def rotation = Lshape1
}

object Lshape1 extends Shape {
  val grid=Array (false, true, true, false,
              false, false, true, false,
              false, false, true, false,
              false, false, false, false)
  def rotation = Lshape2
}

object Lshape2 extends Shape {
  val grid=Array (false, false, false, true,
              false, true, true, true,
              false, false, false, false,
              false, false, false, false)
  def rotation = Lshape3
}

object Lshape3 extends Shape {
  val grid=Array (false, false, true, false,
              false, false, true, false,
              false, false, true, true,
              false, false, false, false)
  def rotation = Lshape0
}

// Forme R
object Rshape0 extends Shape {
  val grid=Array (false, false, false, false,
              false, true, true, true,
              false, false, false, true,
              false, false, false, false)
  def rotation = Rshape1
}

object Rshape1 extends Shape {
  val grid=Array (false, false, true, false,
              false, false, true, false,
              false, true, true, false,
              false, false, false, false)
  def rotation = Rshape2
}

object Rshape2 extends Shape {
  val grid=Array (false, true, false, false,
              false, true, true, true,
              false, false, false, false,
              false, false, false, false)
  def rotation = Rshape3
}

object Rshape3 extends Shape {
  val grid = Array (false, false, true, true,
              false, false, true, false,
              false, false, true, false,
              false, false, false, false)
  def rotation = Rshape0
}
