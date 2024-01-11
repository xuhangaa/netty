package com.xh.apractice.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.xh.apractice.c4.TestByteBuf.log;

public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.buffer();
        buffer1.writeBytes(new byte[]{1,2,3,4,5});
        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.buffer();
        buffer2.writeBytes(new byte[]{6,7,8,9,10});

//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(buffer1).writeBytes(buffer2);
//        log(buffer);

        CompositeByteBuf byteBufs = ByteBufAllocator.DEFAULT.compositeBuffer();
        byteBufs.addComponents(true,buffer1,buffer2);
        log(byteBufs);
    }
}
