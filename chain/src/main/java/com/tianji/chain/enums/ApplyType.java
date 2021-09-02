package com.tianji.chain.enums;

/**
 * @description: 请求申请类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]
 * @author: xionglin
 * @create: 2021-08-29 16:59
 */
public enum ApplyType {
    APPLY_DATA_AUTH(1,"申请数据授权"),
    OBTAIN_DATA(2,"获取数据"),
    APPLY_BIND_DTID(3,"申请绑定数字身份");


    private Integer code;
    private String message;

    ApplyType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
