package com.jike.demo.util;

/**
 * @Auther: huchao
 * @Date: 2018/8/10 18:01
 * @Description:
 */
public class Result<T> {
    private String msg;
    private int code;
    private T data;

    public Result() {
    }

    /**
     * 成功时候的调用
     *
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result result = new Result();
        result.code = 0;
        result.msg = "成功";
        result.data = data;
        return result;
    }

    /**
     * 成功，不需要传入参数
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success() {
        return (Result<T>) success("");
    }


    /**
     * 失败时候的调用,扩展消息参数
     *
     * @param
     * @param msg
     * @return
     */
    public static <T> Result<T> error(int code, String msg, T data) {
        Result result = new Result();
        result.code = -1;
        result.msg = msg;
        result.data = data;
        return result;
    }
    /**
     * 成功调用，逻辑错误调用
     *
     * @param
     * @param msg
     * @return
     */
    public static <T> Result<T> fail(int code, String msg ) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        return result;
    }

    /**
     * 失败时候的调用
     *
     * @return
     */
    public static <T> Result<T> error(int code, String msg) {
        return (Result<T>) error(code,msg,"");
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
