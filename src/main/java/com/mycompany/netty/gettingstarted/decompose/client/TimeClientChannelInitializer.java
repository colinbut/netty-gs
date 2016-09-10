/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.decompose.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

class TimeClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final TimeDecoder timeDecoder;
    private final TimeClientHandler timeClientHandler;


    TimeClientChannelInitializer() {
        timeDecoder = new TimeDecoder();
        timeClientHandler = new TimeClientHandler();
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(timeDecoder, timeClientHandler);
    }

}
