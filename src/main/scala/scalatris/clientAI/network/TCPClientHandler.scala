package scalatris.clientAI.network

import org.jboss.netty.channel.ChannelHandlerContext
import org.jboss.netty.channel.ChannelStateEvent
import org.jboss.netty.channel.ExceptionEvent
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.channel.SimpleChannelUpstreamHandler
import org.jboss.netty.channel.Channel

import scalatris.lib._


class TCPClientHandler (grid: TetrisGrid) extends SimpleChannelUpstreamHandler {
  private var channel: Channel = null

  def send(s: String) {
    channel.write(s ++ "\n")
  }

  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent)
  {
    println("Connected. " ++ e.getChannel.toString)
    channel = e.getChannel
  }
  
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
    val data = e.getMessage.toString
    val s = data match {
      case "I" | "J" | "L" | "O" | "S" | "T" | "Z" => {
        grid.shape = Shape.fromString(data, 5, 0)
        println("new shape: " ++ grid.shape.toString)
        grid.shapeChanged = true
      }
      case _ => println("woopsy daisy")
    }
  }

  override def exceptionCaught(context: ChannelHandlerContext, e: ExceptionEvent) {
    e.getChannel.close()
  }
}
