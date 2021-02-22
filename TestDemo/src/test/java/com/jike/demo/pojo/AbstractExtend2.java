package com.jike.demo.pojo;

/**
 * @author qu.kun
 * @date 2020/11/5
 * @description
 */
public class AbstractExtend2 extends AbstractTest {
    @Override
    protected void eat(String foodName) {
        System.out.println("AbstractExtend2在吃" + foodName);
    }
}
