package scalatris.server.network

import org.jboss.netty.channel.{
  Channel,
  ChannelHandlerContext,
  ChannelStateEvent,
  ExceptionEvent,
  MessageEvent,
  SimpleChannelUpstreamHandler
}
import java.util.logging.Logger
import org.jboss.netty.channel.ChannelFutureListener

import scalatris._
import scalatris.lib._
import scalatris.server._

/**
 * Gère la réception de commandes de l'AI
 */
class TCPServerHandler (grid: TetrisGrid) extends SimpleChannelUpstreamHandler{

  private val logger = Logger.getLogger(getClass.getName)
  private var channel: Channel = null

  def hasClient = channel != null

  def send(s: String) {
    if (hasClient)
      channel.write(s)
  }


  /*
   * Un client s'est connecté
   */
  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent){
    if (channel == null)
      channel = e.getChannel
  }

  override def channelDisconnected(ctx: ChannelHandlerContext, e: ChannelStateEvent){
    channel = null
  }

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
      case _ =>
    }
  }

  override def exceptionCaught(context: ChannelHandlerContext, e: ExceptionEvent) {
    logger.warning("Exception inconnue : " + e.getCause)
    e.getChannel.close()
  }
}
