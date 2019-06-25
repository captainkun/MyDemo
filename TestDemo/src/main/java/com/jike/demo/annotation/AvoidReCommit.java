package com.jike.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qukun
 * @date 2019/6/11
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidReCommit {
    /**
     * 指定时间内不可重复提交,单位毫秒
     * @return
     */
    long timeout() default 30000 ;
}
