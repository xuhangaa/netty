package com.xh.c3;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class TestJDKFuture {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                log.debug("开始计算");
                Thread.sleep(3000);
                return 50;
            }
        });
        log.debug("等待结果");
        log.debug("结果{}", future.get(2, TimeUnit.SECONDS));

    }
}
