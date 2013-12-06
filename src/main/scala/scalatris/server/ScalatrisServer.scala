package scalatris.server

import java.io._
import java.net.{InetAddress,ServerSocket,Socket,SocketException}

object ScalatrisServer {

  def main(args: Array[String]): Unit = {
      try {
        val listener = new ServerSocket(9999);
        println("Scalatris server starting...")
        while (true)
          listener.accept();
        listener.close()
      }
      catch {
        case e: IOException =>
          System.err.println("Could not listen on port: 9999.");
          System.exit(-1)
      }
    }
}
