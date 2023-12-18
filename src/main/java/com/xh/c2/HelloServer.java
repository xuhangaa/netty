package com.xh.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.SneakyThrows;

public class HelloServer {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        NioEventLoopGroup exec = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(worker) // 1
                .channel(NioServerSocketChannel.class) // 2
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 3
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder())// 5
                                .addLast(new IdleStateHandler(0, 0, 5))
                                .addLast(new MyIdleHandler())
                                .addLast(exec, new SimpleChannelInboundHandler<String>() { // 6
                                    @Override
                                    @SneakyThrows
                                    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
//                                Thread.sleep(20);
                                        System.out.println(msg);
                                        System.out.println(1);
                                        ctx.fireChannelRead(msg);
                                    }
                                });
                        ch.pipeline().addLast(exec,new SimpleChannelInboundHandler<String>() { // 6
                            @Override
                            @SneakyThrows
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                Thread.sleep(20000);
                                System.out.println(2);
                            }
                        });
                    }
                })
                .bind(8081); // 4
    }

    public static class MyIdleHandler extends ChannelDuplexHandler {
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state() == IdleState.READER_IDLE) {
                    System.out.println("读空闲触发！");
                } else if (event.state() == IdleState.WRITER_IDLE) {
                    System.out.println("写空闲触发！");
                } else if (event.state() == IdleState.ALL_IDLE) {
                    System.out.println("读写空闲触发！");
                    throw new Exception("aa");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("捕获异常");
        }
    }
}
