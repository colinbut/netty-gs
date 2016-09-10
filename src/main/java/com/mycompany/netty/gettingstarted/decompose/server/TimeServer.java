/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.decompose.server;

import com.mycompany.netty.gettingstarted.decompose.common.TimeProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;


class TimeServer {

    private static final Logger LOGGER = Logger.getLogger(TimeServer.class);

    private final TimeServerChannelInitializer timeServerChannelInitializer;

    TimeServer() {
        timeServerChannelInitializer = new TimeServerChannelInitializer();
    }


    void run() {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            bootstrapServer(bossGroup, workerGroup)
                .bind(TimeProperties.PORT).sync()
                    .channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    private ServerBootstrap bootstrapServer(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(timeServerChannelInitializer)
            .option(ChannelOption.SO_BACKLOG, TimeProperties.BACKLOG)
            .childOption(ChannelOption.SO_KEEPALIVE, TimeProperties.KEEPALIVE);
        return serverBootstrap;
    }


}
