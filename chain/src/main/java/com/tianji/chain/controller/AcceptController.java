package com.tianji.chain.controller;

import com.tianji.chain.service.ResRecordService;
import com.tianji.chain.utils.Result;
import net.phadata.identity.dtc.entity.VerifiableClaim;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 接受数据回调
 * @author: xionglin
 * @create: 2021-08-30 09:43
 */
@RestController
@RequestMapping("/api/v1/chain/accept")
public class AcceptController {
    private final ResRecordService resRecordService;

    public AcceptController(ResRecordService resRecordService) {
        this.resRecordService = resRecordService;
    }

    @PostMapping(value = "/acceptData")
    public Result acceptData(@RequestBody VerifiableClaim verifiableClaim){
        resRecordService.acceptData(verifiableClaim);
        return Result.success("success",null);
    }

}
