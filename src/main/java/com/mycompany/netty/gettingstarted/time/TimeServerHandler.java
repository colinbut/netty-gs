/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(TimeServerHandler.class);

    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) {
        ByteBufAllocator byteBufAllocator = channelHandlerContext.alloc();
        ByteBuf time = byteBufAllocator.buffer(4);  // 4 bytes = 32-bit

        time.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(time);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (channelFuture == future) {
                    LOGGER.info("Closing");
                    channelHandlerContext.close();
                } else {
                    LOGGER.error("Ooops something went wrong with writing");
                    throw new IllegalStateException("Wrote on the wire and operation completed but future is not right");
                }
            }
        });

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        LOGGER.error(cause);
        channelHandlerContext.close();
    }


}
