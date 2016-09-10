/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

public class TimeClient {

    private static final Logger LOGGER = Logger.getLogger(TimeClient.class);

    private static final String HOST = "localhost";
    private static final int PORT = 8080;


    public static void main(String[] args) {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class) // client side channel so not NioServerSocketChannel
                .option(ChannelOption.SO_KEEPALIVE, true) // no childOption because doesn't have a parent
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new TimeClientHandler());
                    }
                });

            // connect to server
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();

            // wait until connection is closed
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOGGER.error(e);
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
