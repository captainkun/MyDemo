package com.jike.demo.service;


import com.jike.demo.util.Result;

/**
 * @author qukun
 * @date 2019/2/18
 */
public interface PayService {
    /**
     * 消费
     * @param orderId 订单号
     * @param cashierId 收银员号
     * @param money 金额
     * @return
     */
    Result consume(String orderId, String cashierId, String money);

    /**
     * 消费查询
     * @param orderId 订单号
     * @param cashierId 收银员号
     * @return
     */
    Result queryConsume(String orderId,String cashierId);

    /**
     * pos机支付系统回调接口
     * @param params 支付结果
     * @return RespCode RespDesc TxnAnsTime
     */
    Result callback(String params);
}
