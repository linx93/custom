package com.tianji.chain.service.impl;

import com.alibaba.fastjson.JSON;
import com.tianji.chain.model.bo.AppBO;
import com.tianji.chain.service.CheckReqService;
import com.tianji.chain.utils.HttpClientUtil;
import com.tianji.chain.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @description: 检查应用id、签名、随机数
 * @author: xionglin
 * @create: 2021-09-01 19:02
 */
@Service
public class CheckReqServiceImpl implements CheckReqService {

    @Value("${saas.dtc.check:}")
    private String checkUrl;

    @Override
    public boolean check(AppBO appBO) {

        Result<Boolean> booleanResult = HttpClientUtil.postForObject(checkUrl, JSON.toJSONString(appBO), Boolean.class, null);
        if (!"200000".equals(booleanResult.getCode())) {
            return false;
        }
        if (booleanResult.getPayload() == null) {
            return false;
        }
        if (!booleanResult.getPayload()) {
            return false;
        }
        return true;
    }
}
