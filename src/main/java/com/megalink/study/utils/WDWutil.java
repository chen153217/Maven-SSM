package com.megalink.study.utils;

/**
 * Created by chenhansen on 2017/7/26.
 */
public class WDWutil {
    public static boolean isExcel2003(String filePath){
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
    public static boolean isExcel2007(String filePath){
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
