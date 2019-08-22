package com.jike.demo.util;

import com.jike.demo.service.IProducer;
import com.jike.demo.service.impl.Producer;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;

/**
 * @author qukun
 * @date 2019/8/8
 */
public class Main {
    public static void main(String[] args) {
        executeCglib();
    }

    /**
     * 基于接口进行代理
     */
    private static void executeProxy() {
        Producer producer = new Producer();
        IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(), producer.getClass().getInterfaces(), (proxy, method, args) -> {
            Object returnValue = null;
            BigDecimal arg = (BigDecimal) args[0];
            System.out.println("*************");
            method.invoke(producer, arg.multiply(BigDecimal.valueOf(10)));
            System.out.println("代理执行");
            return returnValue;
        });
        proxyProducer.saleProduct(BigDecimal.valueOf(1000));
    }

    /**
     * 基于子类进行代理
     */
    private static void executeCglib() {
        Producer producer = new Producer();
        Producer cglibProducer = (Producer) Enhancer.create(producer.getClass(), (MethodInterceptor) (o, method, objects, methodProxy) -> {
            Object returnValue = null;
            BigDecimal arg = (BigDecimal) objects[0];
            if ("afterService".equals(method.getName())) {
                returnValue = method.invoke(producer, arg.add(BigDecimal.valueOf(90000)));
            } else {
                returnValue = method.invoke(producer, arg);
            }

            return returnValue;
        });
        cglibProducer.afterService(BigDecimal.valueOf(3000));
    }

}
