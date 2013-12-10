package scalatris.server

import scalatris._
import scalatris.lib._
import scalatris.server.ui._
import scalatris.server.network._

import swing._
import event.Key._
import swing.event._


import java.awt.{Color => AWTColor}

object ScalatrisServer extends SimpleSwingApplication {

  val grid = new TetrisGrid(10, 20)

  // serveur tcp pour communiquer avec l'AI
  // (mauvaise encapsulation...)
  val tcpHandler = new TCPServerHandler(grid)
  val tcpPF = new TCPServerPipelineFactory(tcpHandler)
  val tcpServer = new TCPServer(tcpPF)

  val canvas = new GridCanvas(grid)

  def top = new MainFrame {
    title = "Scalatris"
    contents = mainPanel
    }

  def onKeyPress(keyCode: Value) = keyCode match {
    case Left | H => grid.move(DirLeft)
    case Right | L => grid.move(DirRight)
    case Down | J => grid.move(DirDown)
    case Up | R | K => grid.move(Rotation)
    case Space => grid.fall
    case Escape | Q => quit
    case _ => 
  }

  val timer = Timer(2000/(2*(grid.level+1))) {
    grid.move(DirDown)
  }


  def mainPanel = new BoxPanel(Orientation.Horizontal) {
    preferredSize = new Dimension(640, 480)
    contents += canvas

    focusable = true
    requestFocus

    listenTo(keys)

    val timer = Timer(100) { repaint }

    reactions += {
      case KeyPressed(_, key, _, _) =>
        onKeyPress(key)
    }
  }

  override def quit {
    super.quit
  }
}
