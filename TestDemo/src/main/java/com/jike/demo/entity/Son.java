package com.jike.demo.entity;

import java.util.Objects;

/**
 * @author qukun
 * @Description
 * @date 2019/12/26
 */
public class Son extends Father {
    private String name;
    private String age;

    @Override
    public void eat() {
        System.out.println("我在吃*");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Son{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Son son = (Son) o;
        return Objects.equals(name, son.name) &&
                Objects.equals(age, son.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
