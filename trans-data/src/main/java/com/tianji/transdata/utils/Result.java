package com.tianji.transdata.utils;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;

/**
 * @description:
 * @Author: kb
 * @Date: 2020-03-26 15:04
 */
@Data
public class Result<T> {
    //返回码
    private String code;
    //提示信息
    private String message;
    //返回具体内容
    private T payload;
    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, T payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
    }

    public Result() {
    }


    public static <T> Result<T> success( String message, T payload){
        return new Result("200",  message, payload);
    }

    public static <T> Result<T> fail(String message, T payload){
        return new Result("500",  message, payload);
    }

    public static <T> Result<T> parse(String responseText, Class<T> clazz) {
        return JSONObject.parseObject(responseText, new TypeReference<>(clazz){});
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", payload=" + payload +
                '}';
    }
}
