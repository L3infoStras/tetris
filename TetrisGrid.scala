import Array._
import scala.util._

class TetrisGrid(nbCol:Int,nbRaw:Int) {
  val allShape =Array(Tshape0,Oshape0,Ishape0,
                      Sshape0,Zshape0,Lshape0,Rshape0)
  val defaultAbs = (-1) // si la forme est hors de grille,
                      // on considère que ses coordonnées
                      // sont négatives
  val defaultOrd = 3

                
  var grid= new Array[Boolean](nbCase)
  var curShape: Shape = getNewShape
  var shapeAbs = defaultAbs
  var shapeOrd = defaultOrd

  //section de la grille à proprement parler.
  def nbCase: Int= {
    nbCol*nbRaw
  }

  def getCase (i:Int,j:Int): Boolean= {
    grid.apply(i*nbCol+j)
  }

  def setCase (i:Int,j:Int) (value:Boolean): Unit= {
    grid.update(i*nbCol+j,value)
  }

  def printGrid: Unit= {
    for (i<- 0 until nbRaw) {
      for (j<- 0 until nbCol) {
        getCase(i,j) match {
          case true =>
            if ((i*nbCol+j+1)%10==0) println(". ")
            else print(". ")
          case false => 
            if ((i*nbCol+j+1)%10==0) println("_ ")
            else print("_ ")
        }
      } 
    }
  }

  //section de la forme actuelle.
  def getNewShape: Shape = 
    allShape.apply(Random.nextInt(7)) 

  def drawIn (b:Boolean)= {// projette la forme actuelle sur la grille
    def f(k:Int,l:Int) = setCase (k,l) _
    def g(k:Int,l:Int) = curShape.getCase(k-shapeAbs,l-shapeOrd)
    for {
      i <- shapeAbs until curShape.dim+shapeAbs  
      j <- shapeOrd until curShape.dim+shapeOrd 
      if ((i>=0)&&(j>=0)&&(j<nbCol))
    } yield if (g(i,j)) f(i,j) (b)
  }
  

/*  def mvShapeLeft: Unit = {
    def reachRight: Boolean = {} // détermine si le mouvement est possible
  }
  def mvShapeRight: Unit = {
    def reachBot: Boolean = {} // détermine si la forme a atteint le niveau le plus bas
  }
  def mvShapeDown: Unit = {
    def reachLeft: Boolean = {} // détermine si le mouvement à gauche est possible
  }
  def rotateShape: Unit = {
    def rotPossible: Boolean = {} // détermine si la forme peut tourner
  }*/
}
