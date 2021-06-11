package com.jike.demo.headfirst.wepon;

/**
 * @author qu.kun
 * @date 2021/5/25
 */
public abstract class Character {
    WeaponBehavior weaponBehavior;

    public abstract void fight();

    public void setWeaponBehavior(WeaponBehavior weaponBehavior) {
        this.weaponBehavior = weaponBehavior;
    }

    public void fightWithWeapon() {
        weaponBehavior.useWeapon();
    }
}
