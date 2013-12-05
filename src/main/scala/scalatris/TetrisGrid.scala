package scalatris

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
    if((i<0)||(i>=nbRaw)||(j<0)||(j>=nbCol)) true
    else grid.apply(i*nbCol+j)
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

  // fonctions de projection et d'effacement sur la grille
  private def drawAux (b:Boolean)= {// projette la forme actuelle sur la grille
    def f (k:Int,l:Int) = setCase (k,l) _
    def g (k:Int,l:Int) = curShape.getCase(k-shapeAbs,l-shapeOrd)
    for {
      i <- shapeAbs until curShape.dim+shapeAbs  
      j <- shapeOrd until curShape.dim+shapeOrd 
      if ((i>=0)&&(j>=0)&&(nbCol>j)&&(nbRaw>i))
    } yield if (g(i,j)) f(i,j) (b)
  }

  def draw = drawAux (true)
  def erase = drawAux (false)
  
  //detecte une collision true si collision false sinon
  def collision = {
    def f (p:Int,q:Int) = getCase(p+shapeAbs,q+shapeOrd)
    def g (k:Int,l:Int) = curShape.getCase(k,l)
    (for {
      i <- 0 until curShape.dim
      j <- 0 until curShape.dim
      // ligne suivante pas vraiment nécessaire mais bon
      //if ((i+shapeAbs>=0)&&(j+shapeOrd>=0)&&(i<nbRaw)&&(j+shapeOrd<nbCol)) 
    } yield (g(i,j)&&f(i,j))) reduceLeft (_||_)
  }

  // déplace la forme vers la gauche après avoir déterminé si le déplacement était possible 
  def mvShapeLeft: Unit = {
    erase
    shapeOrd=shapeOrd-1
    if (collision) {
      shapeOrd=shapeOrd+1
    }
    draw
  }

  // déplace la forme vers la droite après avoir déterminé si le déplacement était possible 
  def mvShapeRight: Unit = {
    erase
    shapeOrd=shapeOrd+1
    if (collision) {
      shapeOrd=shapeOrd-1
    }
    draw
  }

  // déplace la forme vers le bas après avoir déterminé si le déplacement était possible 
  def mvShapeDown: Unit = {
    erase
    shapeAbs=shapeAbs+1
    if (collision) {
      shapeAbs=shapeAbs-1
    }
    draw
  }

  // Effectue une rotation si possible de la forme
  def rotateShape: Unit = {
    erase
    var s=curShape
    curShape=curShape.rotation
    if (collision) {
      curShape=s
    }
    draw
  }
}
