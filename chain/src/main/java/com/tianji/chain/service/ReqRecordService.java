package com.tianji.chain.service;

import com.tianji.chain.model.SerialNumber;
import com.tianji.chain.model.bo.DTCResponse;
import com.tianji.chain.model.dto.ApplyDTO;
import com.tianji.chain.utils.Result;
import io.mybatis.service.BaseService;

import com.tianji.chain.model.ReqRecord;

/**
 * req_record - 授权记录
 *
 * @author linx
 */
public interface ReqRecordService extends BaseService<ReqRecord, Long> {

     Result<DTCResponse> execReq(ApplyDTO applyDTO, SerialNumber serialNumber);
}
