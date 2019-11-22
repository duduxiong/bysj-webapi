package com.lzy.mtnj.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常，用于系统的业务逻辑异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private int code;
    public BusinessException(int code,String message){
        super(message);
        this.code=code;
    }
}
