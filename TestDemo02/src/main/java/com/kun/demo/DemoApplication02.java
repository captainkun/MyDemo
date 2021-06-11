package com.kun.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author qukun
 * @date 2019/3/1
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class DemoApplication02 {
    public static void main(String[] args) {
        boolean isRunOk = true;
        try {
            SpringApplication.run(DemoApplication02.class,args);
        } catch (Exception e) {
            isRunOk = false;
            e.printStackTrace();
        }
    }
}
