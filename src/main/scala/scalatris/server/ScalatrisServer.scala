package scalatris.server

import scalatris._
import scalatris.lib._
import scalatris.server.ui._
import scalatris.server.network._

import swing._
import event.Key._
import swing.event._

import javax.swing.{AbstractAction, Timer => SwingTimer}
import java.awt.event.ActionEvent


import java.awt.{Color => AWTColor}

object ScalatrisServer extends SimpleSwingApplication {

  val grid = new TetrisGrid(10, 20)

  // serveur tcp pour communiquer avec l'AI
  // (mauvaise encapsulation...)
  val tcpHandler = new TCPServerHandler(grid)
  val tcpPF = new TCPServerPipelineFactory(tcpHandler)
  val tcpServer = new TCPServer(tcpPF)

  val canvas = new GridCanvas(grid)

  var currentShape = grid.shape
  var hasClient = false

  def newTimer(interval: Int, op: => Unit): SwingTimer = {
    val timeOut = new AbstractAction() {
      def actionPerformed(e : ActionEvent) = op
    }
    val t = new SwingTimer(interval, timeOut)
    t.setRepeats(true)
    t.start
    t
  }

  def gravity {
    grid.move(DirDown)
  }
  //val gravityTimer = newTimer(1000, gravity)

  /*
   * Timer principal, qui s'occupe de surveiller les changements de
   * formes, et les connexions/déconnexions de l'AI
   * 
   * Tout cela est bien sale. Il aurait été plus judicieux
   * d'utiliser un système équivalents aux signals-slots de Qt, il y
   * en a plusieurs pour scala, mais on a pas eu le temps de les
   * étudier.
   */
  def update {
    // on regarde si la forme a changé
    if (grid.shapeChanged) {
      tcpHandler.send(grid.shape.toString)
      grid.shapeChanged = false
    }

    // on vérifie si le client s'est connecté
    // ou déconnecté
    if (tcpHandler.hasClient != hasClient) {
      hasClient = tcpHandler.hasClient
//      hasClient match {
//        case true => gravityTimer.stop
//        case false => gravityTimer.start
//      }
    }
  }

  grid.newShape

  val mainTimer = newTimer(100, update)
  def top = new MainFrame {
    title = "Scalatris"
    contents = mainPanel
    }

  def onKeyPress(keyCode: Value) {
    if (!hasClient) { 
      if (!grid.gameLost) keyCode match {
        case Left | H => grid.move(DirLeft)
        case Right | L => grid.move(DirRight)
        case Down | J => grid.move(DirDown)
        case Up | R | K => grid.move(Rotation)
        case Space => grid.fall
        case Escape | Q => quit
        case _ =>
      }
      else keyCode match {
        case Escape | Q => quit
        case _ =>
      }
    }
  }

  def mainPanel = new BoxPanel(Orientation.Horizontal) {
    preferredSize = new Dimension(640, 480)
    contents += canvas

    focusable = true
    requestFocus

    listenTo(keys)

    val timer = newTimer(100, repaint)

    reactions += {
      case KeyPressed(_, key, _, _) =>
        onKeyPress(key)
    }
  }

  override def quit {
    mainPanel.timer.stop
    // gravityTimer.stop
    mainTimer.stop
    super.shutdown
    super.quit
  }
}
