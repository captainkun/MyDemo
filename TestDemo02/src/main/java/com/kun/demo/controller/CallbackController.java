package com.kun.demo.controller;

import com.kun.demo.pojo.AliCallbackIn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author qu.kun
 * @date 2021/4/2
 */
@RestController
public class CallbackController {
    @PostMapping(value = "/callback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void callback(String expression, @RequestBody String body) {
        System.out.println(LocalDateTime.now() + "：收到回调通知:" + expression);
        System.out.println("body:" + body);
    }
}
