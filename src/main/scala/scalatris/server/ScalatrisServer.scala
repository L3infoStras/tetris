package scalatris.server

import scalatris._
import scalatris.lib._
import scalatris.server.ui._

import swing._
import event.Key._
import scala.swing.event._


//import scala.swing.event._
import java.awt.{BasicStroke}
import java.awt.{Color => AWTColor}

object ScalatrisGui extends SimpleSwingApplication {

  val grid = new TetrisGrid(10, 20)

  val canvas = new GridCanvas(grid)

  val label =  new Label {
      text = "No click yet"
  }

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


  def mainPanel = new BoxPanel(Orientation.Horizontal) {


    preferredSize = new Dimension(640, 480)
    contents += canvas
    contents += label

    focusable = true
    requestFocus

    listenTo(keys)
    reactions += {
      case KeyPressed(_, key, _, _) =>
        onKeyPress(key)
        repaint
    }
  }
}
