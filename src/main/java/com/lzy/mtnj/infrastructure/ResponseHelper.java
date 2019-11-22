package com.lzy.mtnj.infrastructure;

public class ResponseHelper {
    public static <T>ResponseResult<T> OK(T data,String message){
        ResponseResult<T> result =  new ResponseResult<>();
        result.setCode(20000);
        if(data!=null){
            result.setData(data);
        }
        result.setMessage(message);
        return result;
    }

    public static ResponseResult<String>OK(String message){
        return OK(null,message);
    }

    public static <T> ResponseResult<T> OK(T data){
        return OK(data,"success");
    }
    public static <T> ResponseResult<T> ERROR(T data,String message){
        ResponseResult<T>result = new ResponseResult<>();
        result.setCode(50001);
        if(data!=null){
            result.setData(data);
        }
        result.setMessage(message);
        return result;
    }

    public static ResponseResult<String> ERROR(String message){
        return ERROR(null,message);
    }

    public static <T>ResponseResult<T>ERROR(T data){
        return ERROR(data,"error");
    }
}
