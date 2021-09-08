package com.tianji.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.tianji.chain.model.SerialNumber;

/**
 * serial_number - 流水编号表
 *
 * @author linx
 */
public interface SerialNumberService extends IService<SerialNumber> {
   SerialNumber generateSerialNumber(Integer type);
}
