package com.tianji.chain.controller;

import com.tianji.chain.exception.BussinessException;
import com.tianji.chain.model.ResRecord;
import com.tianji.chain.model.SerialNumber;
import com.tianji.chain.model.dto.FindResultDTO;
import com.tianji.chain.service.ResRecordService;
import com.tianji.chain.service.SerialNumberService;
import com.tianji.chain.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 查询响应结果控制器
 * @author: xionglin
 * @create: 2021-08-30 10:44
 */
@RestController
@RequestMapping("/api/v1/chain/res")
public class ResRecordController extends BaseController {


    @GetMapping(value = "/findAuthResult")
    public Result<ResRecord> findAuthResult(FindResultDTO findResultDTO) {
        ResRecord result = findResult(findResultDTO);
        return Result.success("success", result);
    }

    @GetMapping(value = "/findBindResult")
    public Result<ResRecord> findBindResult(FindResultDTO findResultDTO) {
        ResRecord result = findResult(findResultDTO);
        return Result.success("success", result);
    }


    @GetMapping(value = "/findDataResult")
    public Result<ResRecord> findDataResult(FindResultDTO findResultDTO) {
        ResRecord result = findResult(findResultDTO);
        return Result.success("success", result);
    }

}
