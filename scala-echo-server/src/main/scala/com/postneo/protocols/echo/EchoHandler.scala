package com.postneo.protocols
package echo

import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel.{ChannelHandlerContext, ExceptionEvent, MessageEvent, SimpleChannelUpstreamHandler}

/**
 * A simple Netty handler.
 *
 * Ported from http://docs.jboss.org/netty/3.2/xref/org/jboss/netty/example/echo/EchoServerHandler.html
 */
class EchoHandler extends SimpleChannelUpstreamHandler {

    override def messageReceived(context: ChannelHandlerContext, e: MessageEvent): Unit = {
        //e.getChannel().write(e.getMessage())
        // println(e.getMessage() == "test")
        lazy val message = e.getMessage()
        // message match {
        //   case "test" => println("test received")
        //   case "bleh" => println("bleh received")
        //   case _ => println("what the hell was that")
        // }
        if (message eq "test") println("test received")
        else if (message eq "bleh") println("bleh received")
        else println(e.getMessage())
    }
    
    override def exceptionCaught(context: ChannelHandlerContext, e: ExceptionEvent): Unit =  {
        e.getChannel().close()
    }

}
