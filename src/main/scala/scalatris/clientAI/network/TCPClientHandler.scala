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
import org.jboss.netty.channel.Channel

class TCPClientHandler extends SimpleChannelUpstreamHandler {

  private val logger = Logger.getLogger(getClass.getName)
  private var channel: Channel = null

  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent)
  {
    channel = e.getChannel
    send("fall")
  }

  def send(s: String) {
    channel.write(s)
  }
  
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
    val data = e.getMessage.toString
    println(data)
  }

  override def exceptionCaught(context: ChannelHandlerContext, e: ExceptionEvent) {
    // Close the connection when an exception is raised.
    logger.warning("Unexpected exception from downstream." + e.getCause)
    e.getChannel.close()
  }
}
