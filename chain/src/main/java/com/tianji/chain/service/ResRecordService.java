package com.tianji.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.tianji.chain.model.ResRecord;
import net.phadata.identity.dtc.entity.VerifiableClaim;

/**
 * res_record - 授权记录
 *
 * @author linx
 */
public interface ResRecordService extends IService<ResRecord> {

    /**
     * 回调保存数据
     * @param verifiableClaim 这是一个完整的凭证
     */
    void acceptData(VerifiableClaim verifiableClaim);
}
