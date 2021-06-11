package com.jike.demo.service.impl;

import com.jike.demo.service.IProducer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author QuKun
 * @Description
 * @date 2019/8/22
 */
@Service
public class Producer implements IProducer {

    @Override
    @Async
    public void saleProduct(BigDecimal money) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步方法执行了");
        System.out.println("销售成功，销售额：" + money);
    }

    @Override
    public void afterService(BigDecimal money) {
        System.out.println("售后完毕,售后费用：" + money);
    }
}
