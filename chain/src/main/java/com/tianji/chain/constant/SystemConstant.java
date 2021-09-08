package com.tianji.chain.constant;

/**
 * @description: 系统常量
 * @author: xionglin
 * @create: 2021-08-04 23:24
 */
public interface SystemConstant {
    /**
     * bizdata中定义的医疗交易数据的key
     */
    String MEDICAL_TRANSACTION_DATA = "medicalTransactionData";

    /**
     * 授权DTC的bizdata中同意或不同意授权获取数据的key
     */
    String GRANT = "grant";

    /**
     * DTC的bizdata中【同意】获取数据的key  对应的 value
     */
    String AGREE = "agree";

    /**
     * DTC的bizdata中【不同意】获取数据的key  对应的 value
     */
    String DISAGREE = "disagree";


    /**
     * 流水编号
     */
    String SERIAL_NUMBER = "serialNumber";


    /**
     * bizData中一个类型标志，标志 [1:"行为授权"] [1:"拉取数据"]
     */
    String TYPE = "type";

    /**
     * 医疗系统推数据回调地址的key
     */
    String MEDICAL_CHAIN_URL = "medicalChainUrl";

    /**
     * 医疗交易数据平台的数字身份key
     */
    String MEDICAL_TRANSACTION_DATA_PLATFORM_DTID_KEY = "medicalTransactionDataPlatformDtid";


    /**
     * 请求响应的code key
     */
    String RESPONSE_CODE_KEY = "code";

    /**
     * payload
     */
    String PAYLOAD = "payload";

    /**
     * 交易数据平台请求地址key
     */
    String TRANS_DATA_URL = "transDataUrl";
}
