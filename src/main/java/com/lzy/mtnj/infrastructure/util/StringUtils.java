package com.lzy.mtnj.infrastructure.util;

import tk.mybatis.mapper.util.StringUtil;

public class StringUtils {
    public  static  boolean isEmplty(String str){
        if(str == null || str.trim().length() == 0){
           return true;
        }
        return  false;
    }
}
