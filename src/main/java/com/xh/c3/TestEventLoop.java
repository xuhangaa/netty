package com.xh.c3;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        System.out.println(group.next());
        //普通任务
//        group.next().submit(()->{
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("ok");
//        });

        //定时
        group.next().scheduleAtFixedRate(()->{
            log.info("ok");
        },0,1, TimeUnit.SECONDS);

        log.info("main");
    }
}
