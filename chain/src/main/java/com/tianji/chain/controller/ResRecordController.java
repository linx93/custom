package com.tianji.chain.controller;

import com.tianji.chain.exception.BussinessException;
import com.tianji.chain.model.ResRecord;
import com.tianji.chain.model.SerialNumber;
import com.tianji.chain.service.ResRecordService;
import com.tianji.chain.service.SerialNumberService;
import com.tianji.chain.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 查询响应结果控制器
 * @author: xionglin
 * @create: 2021-08-30 10:44
 */
@RestController
@RequestMapping("/api/v1/chain/res")
public class ResRecordController {
    public final ResRecordService resRecordService;
    public final SerialNumberService serialNumberService;


    public ResRecordController(ResRecordService resRecordService, SerialNumberService serialNumberService) {
        this.resRecordService = resRecordService;
        this.serialNumberService = serialNumberService;
    }

    @GetMapping(value = "/findAuthResult")
    public Result<ResRecord> findAuthResult(@RequestParam("serialNumber") String serialNumber) {
        if (serialNumber == null || serialNumber.trim().equals("")) {
            throw new BussinessException("serialNumber参数不能为空");
        }
        ResRecord one = resRecordService.findOne(ResRecord.builder().serialNumber(serialNumber).build());
        if (one != null) {
            //拿到一次数据流水号+1
            SerialNumber find = new SerialNumber();
            find.setSerialNumber(serialNumber);
            SerialNumber result = serialNumberService.findOne(find);
            SerialNumber update = new SerialNumber();
            update.setSerialNumber(serialNumber);
            update.setNumber(result.getNumber() + 1);
            serialNumberService.updateSelective(update, SerialNumber::getSerialNumber);
        }
        return Result.success("success", one);
    }

    @GetMapping(value = "/findBindResult")
    public Result<ResRecord> findBindResult(@RequestParam("serialNumber") String serialNumber) {
        if (serialNumber == null || serialNumber.trim().equals("")) {
            throw new BussinessException("serialNumber参数不能为空");
        }
        ResRecord one = resRecordService.findOne(ResRecord.builder().serialNumber(serialNumber).build());
        if (one != null) {
            //拿到一次数据流水号+1
            SerialNumber find = new SerialNumber();
            find.setSerialNumber(serialNumber);
            SerialNumber result = serialNumberService.findOne(find);
            SerialNumber update = new SerialNumber();
            update.setSerialNumber(serialNumber);
            update.setNumber(result.getNumber() + 1);
            serialNumberService.updateSelective(update, SerialNumber::getSerialNumber);
        }
        return Result.success("success", one);
    }


    @GetMapping(value = "/findDataResult")
    public Result<ResRecord> findDataResult(@RequestParam("serialNumber") String serialNumber) {
        if (serialNumber == null || serialNumber.trim().equals("")) {
            throw new BussinessException("serialNumber参数不能为空");
        }
        ResRecord one = resRecordService.findOne(ResRecord.builder().serialNumber(serialNumber).build());
        if (one != null) {
            //拿到一次数据流水号+1
            SerialNumber find = new SerialNumber();
            find.setSerialNumber(serialNumber);
            SerialNumber result = serialNumberService.findOne(find);
            SerialNumber update = new SerialNumber();
            update.setSerialNumber(serialNumber);
            update.setNumber(result.getNumber() + 1);
            serialNumberService.updateSelective(update, SerialNumber::getSerialNumber);
        }
        return Result.success("success", one);
    }

}
