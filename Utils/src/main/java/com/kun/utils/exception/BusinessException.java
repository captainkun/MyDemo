package com.kun.utils.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -8817499449247839534L;
    private ResultCode resultCode;
    private HttpStatus httpstatus;


    private BusinessException(Throwable cause, ResultCode rc, HttpStatus httpstatus) {
        super(cause.getMessage(), cause);
        this.resultCode = rc;
        this.httpstatus = httpstatus;
    }

    /**
     * @param cause 原始异常
     * @param resultcode 异常状态描述码{@link ResultCode}
     * @param httpstatus 此异常对应的{@link HttpStatus}
     * @return 业务异常
     */
    public static BusinessException of(Throwable cause, ResultCode resultcode, HttpStatus httpstatus){
        return new BusinessException(cause, resultcode, httpstatus);
    }

    public static BusinessException of(String msg, ResultCode resultCode){
        return of(new RuntimeException(msg), resultCode, HttpStatus.OK);
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }

    public HttpStatus getHttpstatus() {
        return httpstatus;
    }
}


