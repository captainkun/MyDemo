package com.jike.demo.headfirst.wepon;

/**
 * @author qu.kun
 * @date 2021/5/25
 */
public class AxeBehavior implements WeaponBehavior{
    @Override
    public void useWeapon() {
        System.out.println("使用斧头击杀");
    }
}
