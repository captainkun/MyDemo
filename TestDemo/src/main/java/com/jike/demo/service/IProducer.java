package com.jike.demo.service;

import java.math.BigDecimal;

/**
 * @author QuKun
 * @Description
 * @date 2019/8/22
 */
public interface IProducer {
    /**
     * 销售
     * @param money 销售价格
     */
    void saleProduct(BigDecimal money);

    /**
     * 售后
     * @param money 售后价格
     */
    void afterService(BigDecimal money);
}
