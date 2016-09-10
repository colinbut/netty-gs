/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.pojo.encoder;

import com.mycompany.netty.gettingstarted.pojo.model.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class SimpleTimeEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object message, ChannelPromise channelPromise) {
        UnixTime unixTime = (UnixTime) message;
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer(4);
        byteBuf.writeInt((int) unixTime.value());
        channelHandlerContext.write(byteBuf, channelPromise);
    }
}
