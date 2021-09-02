package com.tianji.chain.service.impl;

import io.mybatis.service.AbstractService;

import com.tianji.chain.service.SerialNumberService;
import com.tianji.chain.mapper.SerialNumberMapper;
import com.tianji.chain.model.SerialNumber;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * serial_number - 流水编号表
 *
 * @author linx
 */
@Service
public class  SerialNumberServiceImpl extends AbstractService<SerialNumber, Long, SerialNumberMapper> implements SerialNumberService {

    @Override
    public SerialNumber generateSerialNumber(Integer type) {
        //生成
        String uuid = UUID.randomUUID().toString();
        //保存
        SerialNumber serialNumber = new SerialNumber();
        serialNumber.setType(type);
        serialNumber.setSerialNumber(uuid);
        serialNumber.setCreateTime(new Date());
        SerialNumber save = save(serialNumber);
        return save;
    }
}
