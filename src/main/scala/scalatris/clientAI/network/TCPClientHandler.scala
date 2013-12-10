package scalatris.clientAI.network

import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.channel.ChannelHandlerContext
import org.jboss.netty.channel.ChannelStateEvent
import org.jboss.netty.channel.ExceptionEvent
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.channel.SimpleChannelUpstreamHandler

class EchoClientHandler extends SimpleChannelUpstreamHandler {

  private val logger = Logger.getLogger(getClass.getName)

  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent) {  }

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {

    // donnée envoyée par le serveur
    val data = e.getMessage.toString

    e.getChannel.write(e.getMessage)

  }

  override def exceptionCaught(context: ChannelHandlerContext, e: ExceptionEvent) {
    // Close the connection when an exception is raised.
    logger.warning("Unexpected exception from downstream." + e.getCause)
    e.getChannel.close()
  }
}
