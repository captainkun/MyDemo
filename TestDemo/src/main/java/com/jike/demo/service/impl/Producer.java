package com.jike.demo.service.impl;

import com.jike.demo.service.IProducer;

import java.math.BigDecimal;

/**
 * @author QuKun
 * @Description
 * @date 2019/8/22
 */
public class Producer implements IProducer {

    @Override
    public void saleProduct(BigDecimal money) {
        System.out.println("销售成功，销售额：" + money);
    }

    @Override
    public void afterService(BigDecimal money) {
        System.out.println("售后完毕,售后费用：" + money);
    }
}
