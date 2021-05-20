package com.kun.alibaba.controller;

import com.kun.alibaba.client.Demo01Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qu.kun
 * @date 2021/5/19
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {
    @Value("${useLocalCache:false}")
    private boolean useLocalCache;
    @Resource
    private Demo01Client demo01Client;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }

    @GetMapping("/feignTest")
    public void feignTest() {
        demo01Client.test("哈哈哈哈哈哈");
    }
}
