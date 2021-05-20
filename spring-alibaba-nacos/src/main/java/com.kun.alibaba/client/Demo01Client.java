package com.kun.alibaba.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qu.kun
 * @date 2021/5/20
 */
@FeignClient(value = "service-demo01")
public interface Demo01Client {

    @GetMapping("/feign")
    void test(@RequestParam("name") String name);
}
