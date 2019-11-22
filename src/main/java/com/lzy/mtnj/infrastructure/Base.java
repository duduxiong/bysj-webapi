package com.lzy.mtnj.infrastructure;

import com.lzy.mtnj.model.user.User;
import org.apache.shiro.SecurityUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Base {
    @Autowired
    CacheManager cacheManager;

    public Cache<String,Object> getCache(){
        return cacheManager.getCache("DefaultCache",String.class,Object.class);
    }
    public Cache<String,Object>getLoginCache(){
        return cacheManager.getCache("login",String.class,Object.class);
    }
    public Cache<String,Object>getTempCache(){return  cacheManager.getCache("tmpCache",String.class,Object.class);}

    /**
     * 获取当前用户
     * @return
     */
    public User getCurrentUser(){
        Object object= SecurityUtils.getSubject().getPrincipal();
        return (User)object;
    }


}
