package scalatris.lib

import javax.swing.{AbstractAction, Timer => SwingTimer}
import java.awt.event.ActionEvent

object Timer {
  def apply(interval: Int, repeats: Boolean = true)(op: => Unit) {
    val timeOut = new AbstractAction() {
      def actionPerformed(e : ActionEvent) = op
    }
    val t = new SwingTimer(interval, timeOut)
    t.setRepeats(repeats)
    t.start()
  }

}
