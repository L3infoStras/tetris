package scalatris.server.network

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

import scalatris._
import scalatris.lib._
import scalatris.server._

/**
 * Gère la réception de commandes de l'AI
 */
class TCPServerHandler (grid: TetrisGrid) extends SimpleChannelUpstreamHandler{

  private val logger = Logger.getLogger(getClass.getName)

  override def handleUpstream(ctx: ChannelHandlerContext, e: ChannelEvent){
    e match{
      case c: ChannelStateEvent => logger.info(e.toString)
      case _                    =>
    }
    super.handleUpstream(ctx, e)
  }

/*  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent){
    // Message send to user on connection
    e.getChannel.write(
      "Welcome to " + InetAddress.getLocalHost.getHostName + "!\r\n")
    e.getChannel.write("Scalatris is starting...\r\n")
    // Start tetris game on client computer

  }
 */

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {

    // commande reçue
    val request = e.getMessage.toString.toLowerCase

    var response: String = ""
    var close: Boolean = false

    request match {
      case  "right" => grid.move(DirRight)
      case  "left" => grid.move(DirLeft)
      case  "down" => grid.move(DirDown)
      case  "rotate" => grid.move(Rotation)
      case "fall" => grid.fall
    }

    // val future = e.getChannel.write(response)

    // Close the connection
//    if (close) {
//      future.addListener(ChannelFutureListener.CLOSE)
//    }
  }

  override def exceptionCaught(context: ChannelHandlerContext, e: ExceptionEvent) {
    logger.warning("Exception inconnue : " + e.getCause)
    e.getChannel.close()
  }
}
