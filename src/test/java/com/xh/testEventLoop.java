package com.xh;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class testEventLoop {
    @SneakyThrows
    @Test
    void test2() {
        String host = InetAddress.getLocalHost().getHostName();
        InetAddress[] ia = InetAddress.getAllByName(host);
        for (InetAddress inetAddress : ia) {
            System.out.println(inetAddress);
        }
//        System.out.println(allByName);
    }

    @SneakyThrows
    @Test
    public void test1() {
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
        group.next().scheduleAtFixedRate(() -> {
            log.info("ok");
        }, 0, 1, TimeUnit.SECONDS);

        log.info("main");
    }
}
