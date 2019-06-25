package com.jike.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.jike.demo.service.PayService;
import com.jike.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qukun
 * @date 2019/2/18
 */
@RestController("pay")
public class PayController {

    @Autowired
    private PayService payService;

    @RequestMapping("consume")
    public Result consume(String orderId, String cashierId, String money) {
        return payService.consume(orderId,cashierId,money);
    }

    @RequestMapping("queryConsume")
    public Result queryConsume(String orderId,String cashierId) {
        return payService.queryConsume(orderId,cashierId);
    }

    @RequestMapping("callback")
    public Result callback(String params) {
        return payService.callback(params);
    }

}
