package scalatris.clientAI.network

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory
import org.jboss.netty.channel.{ ChannelFuture, ChannelPipeline, ChannelPipelineFactory, Channels }



class TCPClient (tcpPF: ChannelPipelineFactory) {
  val host = "127.0.0.1"
  val port = 9000

  val bootstrap = new ClientBootstrap(
    new NioClientSocketChannelFactory(Executors.newCachedThreadPool, Executors.newCachedThreadPool))

  bootstrap.setPipelineFactory(tcpPF)

  println("Connecting...")
  val future = bootstrap.connect(new InetSocketAddress(host, port))

  val channel = future.awaitUninterruptibly.getChannel

  bootstrap.releaseExternalResources()
}
