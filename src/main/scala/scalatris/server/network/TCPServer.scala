package scalatris.server.network

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory

/**
 * TCP server for communication between IA and the tetris game
 */
class TCPServer (tcpPF: TCPServerPipelineFactory)  {
    val bootstrap = new ServerBootstrap(
      new NioServerSocketChannelFactory(Executors.newCachedThreadPool, Executors.newCachedThreadPool))

    bootstrap.setPipelineFactory(tcpPF)
    bootstrap.bind(new InetSocketAddress(9000))
}
