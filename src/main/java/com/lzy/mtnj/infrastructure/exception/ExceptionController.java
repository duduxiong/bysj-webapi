package com.lzy.mtnj.infrastructure.exception;

import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Map<String, Object> Unauthorized(ShiroException ex) {
        HashMap<String,Object> result = new HashMap<>();
        result.put("code",401);
        result.put("message","您没有权限执行该操作");
        return result;
    }
    // 捕捉业务异常
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Map<String, Object> BusinessException(BusinessException ex) {
        HashMap<String,Object> result = new HashMap<>();
        result.put("code",ex.getCode());
        result.put("message",ex.getMessage());
        return result;
    }
    // 捕捉所以未处理的异常
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> Exception(Exception ex) {
        HashMap<String,Object> result = new HashMap<>();
        result.put("code",5000000);
        result.put("message","服务器内部异常，请联系管理员");
        return result;
    }
}
