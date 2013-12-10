package scalatris.clientAI.network

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory
import org.jboss.netty.channel.{ ChannelFuture, ChannelPipeline, ChannelPipelineFactory, Channels }



class TCPClient (tcpPF: ChannelPipelineFactory) {
  val host = "127.0.0.1"
  val port = 9000

  // Configure the client
  val bootstrap = new ClientBootstrap(
    new NioClientSocketChannelFactory(Executors.newCachedThreadPool, Executors.newCachedThreadPool))

  bootstrap.setPipelineFactory(tcpPF)

  // Démarre la connexion
  val future = bootstrap.connect(new InetSocketAddress(host, port))

  val channel = connectFuture.awaitUninterruptibly.getChannel

  // Shut down thread pools to exit.
  bootstrap.releaseExternalResources()
}
