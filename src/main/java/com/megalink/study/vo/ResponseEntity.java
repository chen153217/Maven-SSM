package com.megalink.study.vo;

/**
 * Created by chenhansen on 2017/7/21.
 */
public class ResponseEntity<T> {
    private String code;
    private String message;
    private T data;
}
