package com.tianji.chain.controller;

import com.alibaba.fastjson.JSON;
import com.tianji.chain.model.ResRecord;
import com.tianji.chain.model.dto.ApplyBindDTO;
import com.tianji.chain.model.dto.ApplyDataAuthDTO;
import com.tianji.chain.model.dto.ApplyDataDTO;
import com.tianji.chain.service.ApplyService;
import com.tianji.chain.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;


/**
 * req_record - 请求记录
 *
 * @author linx
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/chain/apply")
public class ApplyController extends BaseController {


    private final ApplyService applyService;

    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    /**
     * 申请绑定数字身份
     *
     * @param applyBindDTO
     * @return
     */
    @PostMapping(value = "/applyBind")
    public Result<String> applyBind(@Valid @RequestBody ApplyBindDTO applyBindDTO) {
        ResRecord resRecord = applyService.applyBind(applyBindDTO);
        return Result.success("申请绑定数字身份成功,等待中.....", resRecord.getSerialNumber());
    }


    @PostMapping(value = "/applyDataAuth")
    public Result<String> applyDataAuth(@Valid @RequestBody ApplyDataAuthDTO applyDataAuthDTO) {
        /**
         *     APPLY_DATA_AUTH(1,"申请数据授权"),
         *     OBTAIN_DATA(2,"获取数据"),
         *     APPLY_BIND_DTID(3,"申请绑定数字身份");
         */
        ResRecord resRecord = applyService.applyDataAuth(applyDataAuthDTO);
        return Result.success("申请获取数据的授权成功,等待中.....", resRecord.getSerialNumber());
    }

    @PostMapping(value = "/applyData")
    public Result<String> applyData(@Valid @RequestBody ApplyDataDTO applyDataDTO) {
        ResRecord resRecord = applyService.applyData(applyDataDTO);
        return Result.success("申请获取数据成功,等待中.....", resRecord.getSerialNumber());
    }


    /**
     * 同步获取数据
     *
     * @param applyDataDTO
     * @return
     */
    @PostMapping(value = "/applyDataSync")
    public Result<ResRecord> applyDataSync(@Valid @RequestBody ApplyDataDTO applyDataDTO) {
        ResRecord resRecord = applyService.applyData(applyDataDTO);
        ResRecord result;
        LocalDateTime start = LocalDateTime.now();
        while (true) {
            result = findResult(resRecord.getSerialNumber());
            if (result != null) {
                log.info("sync data :{}", JSON.toJSONString(result,true));
                return Result.success("申请获取数据成功", result);
            }
            LocalDateTime end = LocalDateTime.now();
            long minutes = Duration.between(start, end).toMinutes();
            if (minutes > applyDataDTO.getMinutes()) {
                //默认两分钟
                log.info("applyDataSync :申请获取数据超时,超时时间为:【{}】分钟", applyDataDTO.getMinutes());
                return Result.fail("申请获取数据超时", null);
            }
        }
    }

}
