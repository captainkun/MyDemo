package com.jike.demo.entity;

import java.io.Serializable;

/**
 * @author qukun
 * @date 2020.09.16
 */
public class SerializerWrapper<T> implements Serializable {
    private static final long serialVersionUID = 7789079670309074503L;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> SerializerWrapper<T> builder(T data) {
        SerializerWrapper<T> wrapper = new SerializerWrapper<>();
        wrapper.setData(data);
        return wrapper;
    }
}
