package com.tianji.chain.service.impl;

import com.alibaba.fastjson.JSON;
import com.tianji.chain.constant.SystemConstant;
import com.tianji.chain.exception.BussinessException;
import io.mybatis.service.AbstractService;

import com.tianji.chain.service.ResRecordService;
import com.tianji.chain.mapper.ResRecordMapper;
import com.tianji.chain.model.ResRecord;
import lombok.extern.slf4j.Slf4j;
import net.phadata.identity.dtc.entity.VerifiableClaim;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * res_record - 授权记录
 *
 * @author linx
 */
@Service
@Slf4j
public class ResRecordServiceImpl extends AbstractService<ResRecord, Long, ResRecordMapper> implements ResRecordService {

    @Override
    public void acceptData(VerifiableClaim verifiableClaim) {
        Map<String, Object> bizData = verifiableClaim.getCredentialSubject().getBizData();
        if (bizData == null || bizData.isEmpty()) {
            throw new BussinessException("bizData is empty");
        }
        String type = bizData.getOrDefault(SystemConstant.TYPE, "-1").toString();
        log.info(" accept Data start");
        switch (type) {
            case "1":
                ResRecord buildAuth = ResRecord.builder()
                        .bizData(JSON.toJSONString(bizData))
                        .expire(verifiableClaim.getExpirationDate())
                        .holder(verifiableClaim.getCredentialSubject().getId())
                        .issuer(verifiableClaim.getIssuer())
                        .resTime(new Date())
                        .serialNumber(bizData.getOrDefault(SystemConstant.SERIAL_NUMBER, "-1").toString())
                        .type(verifiableClaim.getType().stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .resType(type)
                        .claim(JSON.toJSONString(verifiableClaim))
                        .build();
                save(buildAuth);
                log.info("accept Data 行为授权");
                log.info("verifiableClaim--->" + verifiableClaim);
            case "2":
                ResRecord buildData = ResRecord.builder()
                        .bizData(JSON.toJSONString(bizData))
                        .expire(verifiableClaim.getExpirationDate())
                        .holder(verifiableClaim.getCredentialSubject().getId())
                        .issuer(verifiableClaim.getIssuer())
                        .resTime(new Date())
                        .serialNumber(bizData.getOrDefault(SystemConstant.SERIAL_NUMBER, "-1").toString())
                        .type(verifiableClaim.getType().stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .resType(type)
                        .claim(JSON.toJSONString(verifiableClaim))
                        .build();
                save(buildData);
                log.info("accept Data 拉取数据");
                log.info("verifiableClaim--->" + verifiableClaim);
            case "3":
                ResRecord buildBind = ResRecord.builder()
                        .bizData(JSON.toJSONString(bizData))
                        .expire(verifiableClaim.getExpirationDate())
                        .holder(verifiableClaim.getCredentialSubject().getId())
                        .issuer(verifiableClaim.getIssuer())
                        .resTime(new Date())
                        .serialNumber(bizData.getOrDefault(SystemConstant.SERIAL_NUMBER, "-1").toString())
                        .type(verifiableClaim.getType().stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .resType(type)
                        .claim(JSON.toJSONString(verifiableClaim))
                        .build();
                save(buildBind);
                log.info("accept data 绑定dtid的记录");
                log.info("verifiableClaim--->" + verifiableClaim);
            default:
                throw new BussinessException("bizData中的type参数有问题[1:获取申请授权结果][3:获取申请绑定结果][2:获取交易数据结果]");
        }
    }
}
