package com.lzy.mtnj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.lzy.mtnj.infrastructure.BaseController;
import com.lzy.mtnj.infrastructure.exception.BusinessException;
import com.lzy.mtnj.model.user.User;
import com.lzy.mtnj.service.system.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {
    @Autowired
    CacheManager cacheManager;
    @Autowired
    UserService userService;

    @GetMapping("getCurrentUser")
    public String getUser(){
        return JSON.toJSONString(getCurrentUser());
    }

    @GetMapping("/getbyid")
    public String getById(String id)throws Exception{
        User user = userService.findById(id);
        return JSON.toJSONString(user,SerializerFeature.WriteMapNullValue);
    }
    @GetMapping("/getfromcache")
    public String getfromcache(String id)throws Exception{
        User user =(User) cacheManager.getCache("DefaultCache", String.class, Object.class).get("user_1");
        return JSON.toJSONString(user,SerializerFeature.WriteMapNullValue);
    }
    @GetMapping("/all")
    public String getAll(@RequestParam("pageIndex")int pageIndex,@RequestParam("pageSize")int pageSize)throws Exception{
        User user = new User();
        user.setPage(pageIndex);
        user.setLimit(pageSize);
        Page<User> users = userService.findByPage(user);
        users.getTotal();
        return "{\"total\":"+ users.getTotal() +",\"data\":"+  JSON.toJSONString(users) +"}";
    }
    @GetMapping("/add")
    public String add(){
        User user = new User();
        user.setId("1");
        userService.add(user);
        return  "OK";
    }
    @GetMapping("/update")
    public String update(){
        User user = new User();
        user.setId(getCurrentUser().getId());
        userService.update(user);
        return  "OK";
    }

    @GetMapping("/cache/add")
    @RequiresPermissions("001")
    public String putCache()throws BusinessException {
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid1", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid2", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid3", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid4", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid5", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid6", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid7", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid8", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid9", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid10", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid11", LocalDateTime.now().toString());
        cacheManager.getCache("DefaultCache", String.class, Object.class).put("uid12", LocalDateTime.now().toString());
        String rs="";
        for(int i=1;i<=12;i++){
            String k = "uid"+String.valueOf(i);
            rs+=" | "+cacheManager.getCache("DefaultCache", String.class, Object.class).get(k).toString();
        }
//        if(true){
//            throw new BusinessException(1111,"系统业务异常=水电费水电费");
//        }
        return rs;
    }
    @GetMapping("/cache/get")
    public String getCache2(){
        Object rs = cacheManager.getCache("DefaultCache", String.class, Object.class).get("uid");
        return rs==null?"":rs.toString();

    }





}
