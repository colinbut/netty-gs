/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.shutdown;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import org.apache.log4j.Logger;

public class Client {

    private static final Logger LOGGER = Logger.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ChannelHandler() {
                            @Override
                            public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Handler Added");
                            }

                            @Override
                            public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Handler Removed");
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                                LOGGER.error(throwable);
                                ChannelFuture channelFuture = channelHandlerContext.close();
                                if (channelFuture.isSuccess()) {
                                    LOGGER.info("ChannelHandlerContext closed");
                                }
                            }
                        });
                    }
                });

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();

            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw e;
        } finally {
            Future future = workerGroup.shutdownGracefully();
            if (future.isSuccess()) {
                LOGGER.info("Shutdown complete - Success");
            } else if (future.isCancelled()) {
                LOGGER.info("Shutdown complete - Cancelled");
            } else if (future.isDone()) {
                LOGGER.info("Shutdown complete - Done");
            }

        }

    }
}
