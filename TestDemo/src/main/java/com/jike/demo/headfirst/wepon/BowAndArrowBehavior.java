package com.jike.demo.headfirst.wepon;

/**
 * @author qu.kun
 * @date 2021/5/25
 */
public class BowAndArrowBehavior implements WeaponBehavior{
    @Override
    public void useWeapon() {
        System.out.println("使用弓箭击杀");
    }
}
