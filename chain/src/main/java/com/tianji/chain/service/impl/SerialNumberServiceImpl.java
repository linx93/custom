package com.tianji.chain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tianji.chain.exception.BussinessException;
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
public class SerialNumberServiceImpl extends ServiceImpl<SerialNumberMapper, SerialNumber> implements SerialNumberService {

    @Override
    public SerialNumber generateSerialNumber(Integer type) {
        //生成
        String uuid = UUID.randomUUID().toString();
        //保存
        SerialNumber serialNumber = new SerialNumber();
        serialNumber.setType(type);
        serialNumber.setSerialNumber(uuid);
        serialNumber.setCreateTime(new Date());
        serialNumber.setNumber(0);
        boolean save = save(serialNumber);
        if (!save) {
            throw new BussinessException("生成流水号实体时保存失败");
        }
        QueryWrapper<SerialNumber> qw = new QueryWrapper<>();
        qw.eq("serial_number", uuid);
        SerialNumber one = getOne(qw);
        return one;
    }
}
