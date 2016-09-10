/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;

class DiscardServerHandler2 extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(DiscardServerHandler2.class);

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object message) {
        ByteBuf in = (ByteBuf) message;
        try {
            while (in.isReadable()) {
                LOGGER.info(in.readByte());
            }
        } finally {
            ReferenceCountUtil.release(message);
            //in.release(); < this is also possible
        }
    }

}
