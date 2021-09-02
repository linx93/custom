package com.tianji.transdata.service;


import com.tianji.transdata.model.ReqInfoDTO;

/**
 * @description: 请求交易平台数据实现
 * @author: xionglin
 * @create: 2021-08-31 09:46
 */
public interface ReqTransDataService {

    /**
     * 1执行请求交易平台数据  2再调用saas的createClaim接口
     * @param reqInfoDTO
     * @return
     */
    String reqTranData(ReqInfoDTO reqInfoDTO);
}
