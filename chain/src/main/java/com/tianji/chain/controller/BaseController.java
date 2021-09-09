package com.tianji.chain.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianji.chain.exception.BussinessException;
import com.tianji.chain.model.ResRecord;
import com.tianji.chain.model.SerialNumber;
import com.tianji.chain.model.bo.AppBO;
import com.tianji.chain.model.dto.FindResultDTO;
import com.tianji.chain.service.CheckReqService;
import com.tianji.chain.service.ResRecordService;
import com.tianji.chain.service.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 封装公共逻辑
 *
 * @author linx
 */
public class BaseController {
    @Autowired
    private CheckReqService checkReqService;
    @Autowired
    private ResRecordService resRecordService;
    @Autowired
    public SerialNumberService serialNumberService;

    protected ResRecord findResult(FindResultDTO findResultDTO) {
        String serialNumber = findResultDTO.getSerialNumber();
        if (serialNumber == null || serialNumber.trim().equals("")) {
            throw new BussinessException("serialNumber参数不能为空");
        }
        AppBO appBO = new AppBO();
        appBO.setSignature(findResultDTO.getSignature());
        appBO.setRand(findResultDTO.getRand());
        appBO.setAppId(findResultDTO.getAppId());
      /*   boolean check = checkReqService.check(appBO);
        if (!check) {
            throw new BussinessException("验签不过!请检查再尝试");
        }*/
        QueryWrapper<ResRecord> qw = new QueryWrapper<>();
        qw.eq("serial_number", serialNumber);
        ResRecord one = resRecordService.getOne(qw);
        if (one != null) {
            //拿到一次数据流水号+1
            QueryWrapper<SerialNumber> sqw = new QueryWrapper<>();
            sqw.eq("serial_number", serialNumber);
            SerialNumber result = serialNumberService.getOne(sqw);
            if (result != null) {
                result.setNumber(result.getNumber() + 1);
            }
            serialNumberService.saveOrUpdate(result);
        }
        return one;
    }


}