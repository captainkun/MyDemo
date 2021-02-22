package com.jike.demo.pojo;

/**
 * @author qu.kun
 * @date 2020/11/5
 * @description
 */
public class AbstractExtend extends AbstractTest {
    @Override
    protected void eat(String foodName) {
        System.out.println("AbstractExtend在吃" + foodName);
    }
}
