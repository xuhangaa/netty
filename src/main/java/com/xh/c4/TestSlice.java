package com.xh.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.xh.c4.TestByteBuf.log;

public class TestSlice {
    public static void main(String[] args) {

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes(new byte[]{'1','2','3','4','5','6','7','8','9','0'});
        log(buffer);
        ByteBuf slice1 = buffer.slice(0, 5);
        slice1.retain();
        ByteBuf slice2 = buffer.slice(5, 5);
        log(slice1);
        log(slice2);
        System.out.println("============");
        slice1.setByte(0,'a');
        buffer.release();
        log(slice1);
        slice1.release();
    }
}
