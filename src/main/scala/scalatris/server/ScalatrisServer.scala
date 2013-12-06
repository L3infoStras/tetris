package scalatris.server

import java.io._
import java.net._
import scala.io._

object ScalatrisServer {

  def main(args: Array[String]) {
    val server = new ServerSocket(9999);
    println("Scalatris server starting...")
    while (true) {
      val s = server.accept()
      val in = new BufferedSource(s.getInputStream()).getLines()
      val out = new PrintStream(s.getOutputStream())

      out.println(in.next())
      out.flush()
      s.close()
    }
  }
}
