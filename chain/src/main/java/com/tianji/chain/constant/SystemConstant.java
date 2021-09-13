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
     * bizData中一个类型标志，标志 [1:"申请数据授权"] [2:"获取数据"] [3:申请绑定数字身份]
     */
    String BIZ_DATA_TYPE = "cType";


    /**
     * bizData中的desc描述信息
     */
    String BIZ_DATA_DESC = "desc";

    /**
     * bizData中的title信息
     */
    String BIZ_DATA_TITLE = "title";

    /**
     * bizData中携带的完整凭证
     */
    String BIZ_DATA_CLAIMS = "claims";

    /**
     * bizData中的交易平台数字身份
     */
    String TRANS_PLATFORM_DTID = "transPlatformDtid";

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
