/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.decompose.client;

import com.mycompany.netty.gettingstarted.decompose.common.TimeProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

class TimeClient {

    private static final Logger LOGGER = Logger.getLogger(TimeClient.class);

    private final TimeClientChannelInitializer timeClientChannelInitializer;

    TimeClient() {
        timeClientChannelInitializer = new TimeClientChannelInitializer();
    }

    void run() {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            new Bootstrap().group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, TimeProperties.KEEPALIVE)
                .handler(timeClientChannelInitializer)
                .connect(TimeProperties.HOST, TimeProperties.PORT).sync()
                .channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOGGER.error(e);
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

}
