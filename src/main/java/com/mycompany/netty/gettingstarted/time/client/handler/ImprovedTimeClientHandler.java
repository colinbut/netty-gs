/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.time.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.Date;

public class ImprovedTimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ImprovedTimeClientHandler.class);

    private ByteBuf buf;

    // region - ChannelInboundHandlerAdapter lifecycle methods

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        buf = channelHandlerContext.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        buf.release();
        buf = null;
    }

    // endregion - ChannelInboundHandlerAdapter lifecycle methods


    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object message) {
        ByteBuf byteBuf = (ByteBuf) message;
        buf.writeBytes(byteBuf);
        byteBuf.release();

        if (buf.readableBytes() > 4) {
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            LOGGER.info(new Date(currentTimeMillis));
            channelHandlerContext.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        LOGGER.error(cause);
        channelHandlerContext.close();
    }

}
