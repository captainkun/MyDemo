package com.jike.demo;

import com.kun.utils.common.ServiceStartAlarm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.UnknownHostException;

/**
 * @author qukun
 * @date 2019/3/1
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
public class DemoApplication {
    public static void main(String[] args) throws UnknownHostException {
        try {
            ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
            while (true) {
                String username = run.getEnvironment().getProperty("username");
                String nickname = run.getEnvironment().getProperty("nickname");
                System.out.println("username:" + username + " " + "nickname:" + nickname);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            ServiceStartAlarm.alarm2WechatRobot(e);
            e.printStackTrace();
        }
    }
}
