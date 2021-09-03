package com.tianji.transdata.enums;

/**
 * @description: 请求方式
 * @author: xionglin
 * @create: 2021-09-02 15:06
 */
public enum Method {
    GET("GET", "执行GET请求"),
    POST("POST", "执行POST请求");
    private String type;
    private String message;

    Method(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
