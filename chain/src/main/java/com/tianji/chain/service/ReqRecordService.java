package com.tianji.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianji.chain.model.SerialNumber;
import com.tianji.chain.model.bo.DTCResponse;
import com.tianji.chain.model.dto.ApplyDTO;
import com.tianji.chain.utils.Result;

import com.tianji.chain.model.ReqRecord;

/**
 * req_record - 授权记录
 *
 * @author linx
 */
public interface ReqRecordService extends IService<ReqRecord> {

     Result<DTCResponse> execReq(ApplyDTO applyDTO, SerialNumber serialNumber);
}
