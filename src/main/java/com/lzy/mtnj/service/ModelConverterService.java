package com.lzy.mtnj.service;

import com.lzy.mtnj.infrastructure.shiro.UserInfo;
import com.lzy.mtnj.infrastructure.util.DateUtil;
import com.lzy.mtnj.model.user.User;

public class ModelConverterService {
    public  static UserInfo ToUserInfo(User user){
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName(user.getUserName());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setCellPhone(user.getCellPhone());
        userInfo.setEmail(user.getEmail());
        userInfo.setLastLoginArea(user.getLastLoginArea());
        userInfo.setLastLoginDate(DateUtil.dateFormatToString(user.getLastLoginDate()));
        userInfo.setSuperAdmin(user.getSuperAdmin());
        userInfo.setStatus(user.getStatus());
        userInfo.setSex(user.getSex());
        userInfo.setSexName(user.getSex()==0?"女":"男");
        return  userInfo;
    }
}
