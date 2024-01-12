package com.xh.chat;

import com.xh.chat.message.LoginRequestMessage;
import com.xh.chat.protocol.MessageCodecSharable;
import com.xh.chat.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.context.Theme;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class ChatClient1 {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProcotolFrameDecoder());
                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter());
                }
                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                    System.out.println(1);
                    new Thread(()->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("请输入用户名:");
                        String username = scanner.nextLine();
                        System.out.println("请输入密码:");
                        String password = scanner.nextLine();
                        // 构造消息对象
                        LoginRequestMessage message = new LoginRequestMessage(username, password);
                        // 发送消息
                        ctx.writeAndFlush(message);
                        System.out.println("等待后续操作...");
                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    },"system in").start();
                }

                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                    log.debug("msg:{}",msg);
                }

            });
            Channel channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
