package com.megalink.study.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhansen on 2017/7/21.
 */
public class ExceptionCode {
    public static ExceptionCode exceptionCode = new ExceptionCode();
    private ExceptionCode(){}
    private static final Map<String,String> codeMap = new HashMap<String,String>();
    static {
        codeMap.put("100001","密码不能为空");
    }
    public String getMessage(String code){
        String message = codeMap.get(code);
        if(message==null || "".equals(message)){
            message = "系统繁忙！";
        }
        return message;
    }
}
