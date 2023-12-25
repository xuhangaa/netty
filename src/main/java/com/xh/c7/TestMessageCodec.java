package com.xh.c7;

import com.xh.message.LoginRequestMessage;
import com.xh.protocol.MessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
                new MessageCodec());
        LoginRequestMessage message = new LoginRequestMessage("aa", "11");

//        channel.writeOutbound(message);

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buffer);

        ByteBuf slice = buffer.slice(0, 100);
        ByteBuf slice1 = buffer.slice(100, buffer.readableBytes() - 100);
        slice.retain();
        channel.writeInbound(slice);
        channel.writeInbound(slice1);
    }
}
