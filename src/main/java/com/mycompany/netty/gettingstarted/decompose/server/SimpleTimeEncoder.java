/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.decompose.server;

import com.mycompany.netty.gettingstarted.decompose.common.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

class SimpleTimeEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object message, ChannelPromise channelPromise) {
        UnixTime unixTime = (UnixTime) message;
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer(4);
        byteBuf.writeInt((int) unixTime.getValue());
        channelHandlerContext.write(byteBuf, channelPromise);
    }

}
