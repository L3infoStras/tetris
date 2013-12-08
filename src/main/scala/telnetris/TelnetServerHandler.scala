package telnetris

import java.util.concurrent.atomic.AtomicLong
import org.jboss.netty.buffer.{ChannelBuffer,ChannelBuffers}
import org.jboss.netty.channel.{
  Channel,
  ChannelEvent,
  ChannelHandlerContext,
  ChannelState,
  ChannelStateEvent,
  ExceptionEvent,
  MessageEvent,
  SimpleChannelUpstreamHandler
}
import java.util.logging.Logger
import java.net.InetAddress
import java.util.Date
import org.jboss.netty.channel.ChannelFutureListener

/**
 * Handles a server-side channel.
 */
class TelnetServerHandler extends SimpleChannelUpstreamHandler{

  private val logger = Logger.getLogger(getClass.getName)

  override def handleUpstream(ctx: ChannelHandlerContext, e: ChannelEvent){
    e match{
      case c: ChannelStateEvent => logger.info(e.toString)
      case _                    =>
    }
    super.handleUpstream(ctx, e)
  }

  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent){
    // Message send to user on connection
    e.getChannel.write(
      "Welcome to " + InetAddress.getLocalHost.getHostName + "!\r\n")
    e.getChannel.write("It is " + new Date + " now.\r\n")
  }

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {

    // First we cast the message to a String
    // The result will be a String thanks to the codec in TelnetPipelineFactory
    val request = e.getMessage.toString

    // Create a response (will probably change in a close future)
    var response: String = ""
    var close: Boolean = false
    if (request.length == 0) {
      response = "Please type something.\r\n"
    } else if ("bye".equals(request.toLowerCase())) {
      response = "Have a good day!\r\n"
      close = true
    } else {
      response = "Did you say '" + request + "'?\r\n"
      println(request)
    }

    // Send the response to the user
    // The response is first converted by the pipeline
    val future = e.getChannel.write(response)

    // Close the connection
    if (close) {
      future.addListener(ChannelFutureListener.CLOSE)
    }
  }

  override def exceptionCaught(context: ChannelHandlerContext, e: ExceptionEvent) {
    // Log the error then close the connection if an exception is raised
    logger.warning("Unexpected exception from downstream." + e.getCause)
    e.getChannel.close()
  }
}
