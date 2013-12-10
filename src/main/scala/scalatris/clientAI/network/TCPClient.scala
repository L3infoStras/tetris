package scalatris.clientAI.network

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory
import org.jboss.netty.channel.{ ChannelFuture, ChannelPipeline, ChannelPipelineFactory, Channels }



object TCPClient {
    def main(args: Array[String]) {

    // Parse options.
    val host = "127.0.0.1"
    val port = 9000
    var firstMessageSize: Int = 0

    // Configure the server.
    val bootstrap = new ClientBootstrap(
      new NioClientSocketChannelFactory(Executors.newCachedThreadPool, Executors.newCachedThreadPool))

    // Set up the pipeline factory.
    bootstrap.setPipelineFactory(new ChannelPipelineFactory {
      override def getPipeline: ChannelPipeline = {
        Channels.pipeline(new EchoClientHandler)
      }
    })

    // Start the connection attempt.
    val future = bootstrap.connect(new InetSocketAddress(host, port))

    // Wait until the connection is closed or the connection attempt fails.
    future.getChannel.getCloseFuture.awaitUninterruptibly

    // Shut down thread pools to exit.
    bootstrap.releaseExternalResources()
  }
}
