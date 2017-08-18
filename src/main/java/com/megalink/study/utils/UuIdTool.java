package com.megalink.study.utils;

import java.util.UUID;

/**
 * Created by chenhansen on 2017/7/23.
 */
public class UuIdTool {
    public static String getUuId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
