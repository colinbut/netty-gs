/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * Discards a message received silently
     *
     * {@inheritDoc}
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object message) {
        ((ByteBuf)message).release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {

    }

}
