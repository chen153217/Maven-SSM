package com.megalink.study.vo;

import com.megalink.study.utils.ExceptionCode;

/**
 * Created by chenhansen on 2017/7/21.
 */
public class BussinessException extends RuntimeException{
    private String code;
    private String message;
    public BussinessException(String code){
        this.code=code;
        this.message= ExceptionCode.exceptionCode.getMessage(code);
    }
}
