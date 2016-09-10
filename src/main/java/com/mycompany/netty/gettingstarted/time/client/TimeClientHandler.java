/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.time.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(TimeClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object message) {
        ByteBuf byteBuf = (ByteBuf) message;
        try {
            long timeCurrentMillis = (byteBuf.readUnsignedInt() - 2208988800L) * 1000L;
            LOGGER.info(new Date(timeCurrentMillis));
            channelHandlerContext.close();
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        LOGGER.error(cause);
        channelHandlerContext.close();
    }
}
