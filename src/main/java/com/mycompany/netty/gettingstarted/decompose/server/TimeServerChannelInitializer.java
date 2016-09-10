/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.decompose.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


class TimeServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final TimeEncoder timeEncoder;
    private final SimpleTimeEncoder simpleTimeEncoder;
    private final TimeServerHandler timeServerHandler;

    TimeServerChannelInitializer() {
        timeEncoder = new TimeEncoder();
        simpleTimeEncoder = new SimpleTimeEncoder();
        timeServerHandler = new TimeServerHandler();
    }


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(simpleTimeEncoder, timeServerHandler);
    }

}
