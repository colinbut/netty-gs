/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.decompose.server;

import com.mycompany.netty.gettingstarted.pojo.model.UnixTime;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.writeAndFlush(new UnixTime())
            .addListener(ChannelFutureListener.CLOSE);
    }

}
