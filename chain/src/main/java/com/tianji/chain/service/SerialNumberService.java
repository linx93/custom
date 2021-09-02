package com.tianji.chain.service;

import io.mybatis.service.BaseService;

import com.tianji.chain.model.SerialNumber;

/**
 * serial_number - 流水编号表
 *
 * @author linx
 */
public interface SerialNumberService extends BaseService<SerialNumber, Long> {
   SerialNumber generateSerialNumber(Integer type);
}
