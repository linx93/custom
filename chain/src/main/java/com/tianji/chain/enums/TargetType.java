package com.tianji.chain.enums;

/**
 * @description: targetId
 * @author: xionglin
 * @create: 2021-08-29 20:37
 */
public enum TargetType {
    /**
     * saas 队列1
     */
    SAAS_DTC_01("saas_dtc_01",""),

    /**
     * saas 队列2
     */
    SAAS_DTC_02("saas_dtc_02",""),
    /**
     * saas 队列2
     */
    SAAS_DTC_03("saas_dtc_03",""),
    ;
    private String targetId;
    private String message;

    TargetType(String targetId, String message) {
        this.targetId = targetId;
        this.message = message;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getMessage() {
        return message;
    }
}
