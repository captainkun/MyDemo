package com.jike.demo.pojo;

/**
 * @author qu.kun
 * @date 2020/11/5
 * @description
 */
public abstract class AbstractTest implements TestInterface {
    public final void eatFood(String foodName) {
        eat(foodName);
    }


    protected abstract void eat(String foodName);
}
