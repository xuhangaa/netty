package com.xh.c;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AA {
    @SneakyThrows
    @GetMapping("/aa")
    public void a(){
        Thread.sleep(3000);
        System.out.println("1");
    }
}
