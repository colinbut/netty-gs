/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.shutdown;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.apache.log4j.Logger;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);


    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ChannelInboundHandler() {
                            @Override
                            public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Channel Registered");
                            }

                            @Override
                            public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Channel Unregistered");
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Channel Active");
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Channel InActive");
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                LOGGER.info("Channel Read");
                            }

                            @Override
                            public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Channel Read Complete");
                            }

                            @Override
                            public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                LOGGER.info("User Triggered Event");
                            }

                            @Override
                            public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Channel Writability changed");
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                                LOGGER.info("Exception Caught");
                                LOGGER.error(throwable);
                                ChannelFuture channelFuture = channelHandlerContext.close();
                                if (channelFuture.isDone()) {
                                    LOGGER.info("Done");
                                } else if (channelFuture.isSuccess()) {
                                    LOGGER.info("Success");
                                } else if (channelFuture.isCancelled()) {
                                    LOGGER.info("Cancelled");
                                }
                            }

                            @Override
                            public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Handler added");
                            }

                            @Override
                            public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
                                LOGGER.info("Handler removed");
                            }
                        });
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();

            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOGGER.error(e);
        } finally {

            Future<?> future = workerGroup.shutdownGracefully();
            if (future.isDone()) {
                LOGGER.info("Done");
            } else if (future.isSuccess()) {
                LOGGER.info("Success");
            } else if (future.isCancelled()) {
                LOGGER.info("Cancelled");
            }


            bossGroup.shutdownGracefully();
        }

    }
}
