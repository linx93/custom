package com.tianji.transdata.controller;


import com.tianji.transdata.model.ReqInfoDTO;
import com.tianji.transdata.service.ReqTransDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 请求交易平台数据
 * @author: xionglin
 * @create: 2021-08-30 22:11
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/transData")
public class ReqTransDataController {

    private final ReqTransDataService transDataService;

    public ReqTransDataController(ReqTransDataService transDataService) {
        this.transDataService = transDataService;
    }

    /**
     * 1执行请求交易平台数据  2再调用saas的createClaim接口
     *
     * @param reqInfoDTO
     * @return
     */
    @PostMapping(value = "/reqTranData")
    public Object reqTranData(@RequestBody ReqInfoDTO reqInfoDTO) {
        log.info("入参reqInfoDTO:{}", reqInfoDTO);
        String jsonStr = transDataService.reqTranData(reqInfoDTO);
        return jsonStr;
    }
}
