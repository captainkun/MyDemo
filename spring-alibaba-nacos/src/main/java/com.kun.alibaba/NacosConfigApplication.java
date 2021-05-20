package com.kun.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qu.kun
 * @date 2021/5/19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosConfigApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(NacosConfigApplication.class, args);
        while (true) {
            String username = run.getEnvironment().getProperty("username");
            String age = run.getEnvironment().getProperty("age");
            System.out.println("username:" + username + " " + "age:" + age);
            Thread.sleep(2000);
        }
    }

    @RestController
    public class EchoController {
        @GetMapping(value = "/echo/{string}")
        public String echo(@PathVariable String string) {
            return "Hello Nacos Discovery " + string;
        }
    }
}
