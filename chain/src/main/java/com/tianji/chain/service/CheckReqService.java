package com.tianji.chain.service;

import com.tianji.chain.model.bo.AppBO;

/**
 * @description: 检查应用id、签名、随机数
 * @author: xionglin
 * @create: 2021-09-01 19:01
 */
public interface CheckReqService {
    /** 校验
     * @param appBO
     * @return
     */
    boolean check(AppBO appBO);
}
