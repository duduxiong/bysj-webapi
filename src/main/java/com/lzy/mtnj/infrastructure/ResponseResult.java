package com.lzy.mtnj.infrastructure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;
}
