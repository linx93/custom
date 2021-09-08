package com.tianji.chain.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianji.chain.model.SerialNumber;
import com.tianji.chain.service.SerialNumberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * serial_number - 流水编号表
 *
 * @author linx
 */
@RestController
@RequestMapping("/api/v1/chain/serialNumber")
public class SerialNumberController {


    private final SerialNumberService serialNumberService;

    public SerialNumberController(SerialNumberService serialNumberService) {
        this.serialNumberService = serialNumberService;
    }

    @GetMapping(value = "findAll")
    public List<SerialNumber> serialNumbers() {
        return serialNumberService.list(new QueryWrapper<>());
    }

}
