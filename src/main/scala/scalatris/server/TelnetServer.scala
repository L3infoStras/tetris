package scalatris.server

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory

/**
 * Telnet server for communication between IA and the tetris game
 */
object TelnetServer{
  def main(args: Array[String]){
    // Server configuration
    val bootstrap = new ServerBootstrap(
      new NioServerSocketChannelFactory(Executors.newCachedThreadPool, Executors.newCachedThreadPool))

    // Pipeline factory configuration
    bootstrap.setPipelineFactory(new TelnetServerPipelineFactory)

    // Bind to port and start the server
    bootstrap.bind(new InetSocketAddress(9000))
  }
}
