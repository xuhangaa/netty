package com.xh;

import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class httpTest {

    private static ClientConnectionManager connectionManager;

    @SneakyThrows
    @Test
    public void test3() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ">我是子线程<");
            }
        });


    }

    @SneakyThrows
    public void send(CloseableHttpClient client){
        String url="http://192.168.0.120:9999/aa";
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(3000)
                // 设置请求超时时间(单位毫秒)
//                    .setConnectionRequestTimeout(5)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(1000).build();
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse execute = client.execute(httpGet);
        } catch (IOException e) {
            Thread.sleep(10000);
            System.out.println("chaoshi");
        }
    }

    @SneakyThrows
    @Test
    public void test1() {


        PoolingHttpClientConnectionManager pm = new PoolingHttpClientConnectionManager();
        pm.setDefaultMaxPerRoute(10);
        pm.setMaxTotal(10);
        CloseableHttpClient client = HttpClientBuilder.create().setConnectionManager(pm).build();


//        CloseableHttpClient client = HttpClients.createDefault();
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 100000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    send(client);
                    System.out.println(Thread.currentThread().getName() + ">我是子线程<");
                }
            });

        }
        Thread.sleep(10000000);
    }
    @SneakyThrows
    @Test
    public void test2() {

//        PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
//        CloseableHttpClient client = HttpClients.custom()
//                .setConnectionManager(poolingConnManager)
//                .build();
//        client.execute(new HttpGet("https://www.baeldung.com"));
//
//        poolingConnManager.setMaxTotal();
    }

}
