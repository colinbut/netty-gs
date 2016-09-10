/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.pojo.handler;

import com.mycompany.netty.gettingstarted.pojo.model.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(TimeClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object message) {
        UnixTime unixTime = (UnixTime) message;
        LOGGER.info(unixTime);
        channelHandlerContext.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        LOGGER.error(cause);
        channelHandlerContext.close();
    }
}
