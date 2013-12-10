package scalatris.clientAI

import scalatris.clientAI.network._
import scalatris.server.network._

object AIClient {
  def main(args: Array[String]) {
    val tcpHandler = new TCPClientHandler
    val tcpPF = new TCPServerPipelineFactory(tcpHandler)
    val tcpClient = new TCPClient(tcpPF)

  }
}
