/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * Handles a server-side channel
 */
class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(DiscardServerHandler.class);

    /**
     * Discards a message received silently
     *
     * {@inheritDoc}
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object message) {
        if (((ByteBuf)message).release()) {
            LOGGER.info("Successfully released reference-counted ByteBuf object");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        LOGGER.error(cause);
        channelHandlerContext.close();
    }

}
