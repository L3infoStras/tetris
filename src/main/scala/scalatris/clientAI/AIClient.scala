package scalatris.clientAI

import scalatris.clientAI.network._
import scalatris.server.network._
import scalatris.lib._

import java.lang.Thread

object AIClient {
  def main(args: Array[String]) {
    val grid = new TetrisGrid(10,20)

    val tcpHandler = new TCPClientHandler(grid)
    val tcpPF = new TCPServerPipelineFactory(tcpHandler)
    val tcpClient = new TCPClient(tcpPF)

    while (!grid.shapeChanged) {
      java.lang.Thread.sleep(100)
    }

    println("grille -> " ++ grid.shape.toString)
    var aig = new AIgrid(grid)
    grid.shapeChanged = false
    aig = AI.computeAI(aig)
    var moveList = aig.moveList
    moveList.foreach(d => println(d))
    aig.printGrid

/*    while(!aig.gameLost) {
      if (grid.shapeChanged)
        aig.printGrid
      println(aig.moveList)
      java.lang.Thread.sleep(1000)
 }
 */
  }
}
