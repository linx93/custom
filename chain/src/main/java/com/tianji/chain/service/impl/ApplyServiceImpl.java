package com.tianji.chain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianji.chain.constant.SystemConstant;
import com.tianji.chain.exception.BussinessException;
import com.tianji.chain.mapper.ResRecordMapper;
import com.tianji.chain.model.ResRecord;
import com.tianji.chain.model.SerialNumber;
import com.tianji.chain.model.bo.DTCResponse;
import com.tianji.chain.model.dto.*;
import com.tianji.chain.service.ApplyService;
import com.tianji.chain.service.ReqRecordService;
import com.tianji.chain.service.ResRecordService;
import com.tianji.chain.service.SerialNumberService;
import com.tianji.chain.utils.Result;
import net.phadata.identity.common.DTCType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 请求申请的业务实现
 * @author: xionglin
 * @create: 2021-08-29 18:07
 */
@Service
public class ApplyServiceImpl implements ApplyService {


    public final ReqRecordService reqRecordService;
    public final ResRecordService resRecordService;
    public final SerialNumberService serialNumberService;
    public final ResRecordMapper resRecordMapper;

    public ApplyServiceImpl(ReqRecordService reqRecordService, ResRecordService resRecordService, SerialNumberService serialNumberService, ResRecordMapper resRecordMapper) {

        this.reqRecordService = reqRecordService;
        this.resRecordService = resRecordService;
        this.serialNumberService = serialNumberService;
        this.resRecordMapper = resRecordMapper;
    }

    @Override
    @Deprecated
    public ResRecord executeApply(ApplyDTO applyDTO, Integer type) {
        //生成流水号
        SerialNumber serialNumber = serialNumberService.generateSerialNumber(type);
        if (serialNumber == null || serialNumber.getSerialNumber() == null) {
            throw new BussinessException("生成流水号出错");
        }
        Result<DTCResponse> dtcResponseResult = reqRecordService.execReq(applyDTO, serialNumber);
        if (!"200000".equals(dtcResponseResult.getCode())) {
            throw new BussinessException("请求tdaas创建凭证失败!");
        }
        //查询回推的响应数据
        // ResRecord resRecord = new ResRecord();
        // resRecord.setSerialNumber(serialNumber.getSerialNumber());

        switch (type) {
            case 1:
                //授权
                ResRecord auth = new ResRecord();
                auth.setSerialNumber(serialNumber.getSerialNumber());
                // auth.setExpire(applyDTO.getExpire());
                // auth.setHolder(applyDTO.getHolder());
                // auth.setIssuer(applyDTO.getIssuer());
                return auth;
            case 2:
                //获取数据   取消同步  使用异步，返回流水号
                /*while (true) {
                    List<ResRecord> list = resRecordService.findList(resRecord);
                    if (!list.isEmpty()) {
                        return list;
                    }
                }*/
                ResRecord authData = new ResRecord();
                authData.setSerialNumber(serialNumber.getSerialNumber());
                // authData.setExpire(applyDTO.getExpire());
                // authData.setHolder(applyDTO.getHolder());
                // authData.setIssuer(applyDTO.getIssuer());
                return authData;
            case 3:
                //绑定dtid
                ResRecord bind = new ResRecord();
                bind.setSerialNumber(serialNumber.getSerialNumber());
                // bind.setExpire(applyDTO.getExpire());
                // bind.setHolder(applyDTO.getHolder());
                // bind.setIssuer(applyDTO.getIssuer());
                return bind;
            default:
                throw new BussinessException(" applyTypeCode只能为;  [1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]");
        }
    }

    @Override
    public ResRecord applyBind(ApplyBindDTO applyBindDTO) {
        //生成流水号
        //[(1,"申请数据授权"),(2,"获取数据"),(3,"申请绑定数字身份")]
        SerialNumber serialNumber = serialNumberService.generateSerialNumber(3);
        if (serialNumber == null || serialNumber.getSerialNumber() == null) {
            throw new BussinessException("生成流水号出错");
        }
        //设置描述信息
        Map<String, Object> bizData = new HashMap<>(8);
        if (applyBindDTO.getDesc() != null && "".equals(applyBindDTO.getDesc())) {
            bizData.put(SystemConstant.BIZ_DATA_DESC, applyBindDTO.getDesc());
        }
        ApplyDTO build = ApplyDTO.builder()
                .appId(applyBindDTO.getAppId())
                .signature(applyBindDTO.getSignature())
                .rand(applyBindDTO.getRand())
                .issuer(applyBindDTO.getMedicalChainDtid())
                .holder(applyBindDTO.getBusinessUserDtid())
                .pieces(1)
                .expire(System.currentTimeMillis() / 1000 + 1000000)
                .type(DTCType.TICKET.getType())
                .tdrType("10002")
                .times(0)
                .applyTypeCode(3)
                .bizData(bizData)
                .build();
        Result<DTCResponse> dtcResponseResult = reqRecordService.execReq(build, serialNumber);
        if (!"200000".equals(dtcResponseResult.getCode())) {
            throw new BussinessException("请求tdaas创建凭证失败!  " + dtcResponseResult.getMessage());
        }
        ResRecord resRecord = new ResRecord();
        resRecord.setSerialNumber(serialNumber.getSerialNumber());
        return resRecord;
    }

    @Override
    public ResRecord applyDataAuth(ApplyDataAuthDTO applyDataAuthDTO) {
        //生成流水号
        //[(1,"申请数据授权"),(2,"获取数据"),(3,"申请绑定数字身份")]
        SerialNumber serialNumber = serialNumberService.generateSerialNumber(1);
        if (serialNumber == null || serialNumber.getSerialNumber() == null) {
            throw new BussinessException("生成流水号出错");
        }
        //设置描述信息
        Map<String, Object> bizData = new HashMap<>(8);
        if (applyDataAuthDTO.getDesc() != null && !"".equals(applyDataAuthDTO.getDesc())) {
            bizData.put(SystemConstant.BIZ_DATA_DESC, applyDataAuthDTO.getDesc());
        }
        //授权的时候设置去哪个交易平台拿数据
        bizData.put(SystemConstant.TRANS_PLATFORM_DTID, applyDataAuthDTO.getTransPlatformDtid());
        ApplyDTO build = ApplyDTO.builder()
                .appId(applyDataAuthDTO.getAppId())
                .signature(applyDataAuthDTO.getSignature())
                .rand(applyDataAuthDTO.getRand())
                .issuer(applyDataAuthDTO.getMedicalChainDtid())
                .holder(applyDataAuthDTO.getBusinessUserDtid())
                .pieces(1)
                .expire(System.currentTimeMillis() / 1000 + 1000000)
                .type(DTCType.TICKET.getType())
                .tdrType("10002")
                .times(0)
                .applyTypeCode(1)
                .bizData(bizData)
                .build();
        Result<DTCResponse> dtcResponseResult = reqRecordService.execReq(build, serialNumber);
        if (!"200000".equals(dtcResponseResult.getCode())) {
            throw new BussinessException("请求tdaas创建凭证失败!" + dtcResponseResult.getMessage());
        }
        ResRecord resRecord = new ResRecord();
        resRecord.setSerialNumber(serialNumber.getSerialNumber());
        return resRecord;
    }

    @Override
    public ResRecord applyData(ApplyDataDTO applyDataDTO) {
        //生成流水号
        //[(1,"申请数据授权"),(2,"获取数据"),(3,"申请绑定数字身份")]
        SerialNumber serialNumber = serialNumberService.generateSerialNumber(2);
        if (serialNumber == null || serialNumber.getSerialNumber() == null) {
            throw new BussinessException("生成流水号出错");
        }
        ReqInfoDTO reqInfoDTO = applyDataDTO.getReqInfoDTO();
        Map<String, Object> bizData = new HashMap<>(8);
        //设置医链发过来的请求信息，在消费到获取数据消息时候，利用这些reqInfo去请求数据交易平台
        bizData.put("reqInfoDTO", reqInfoDTO);
        //获取授权的凭证
        QueryWrapper<ResRecord> rw = new QueryWrapper<>();
        rw.eq("serial_number", applyDataDTO.getSerialNumber());
        ResRecord res = resRecordMapper.selectOne(rw);
        if (res == null) {
            throw new BussinessException("没有查询到对应的授权凭证,请确认流水号是否问题");
        }
        //设置授权结果的完整凭证claims到bizdata中，用于判断是否能获取数据的根据
        bizData.put(SystemConstant.BIZ_DATA_CLAIMS, res.getClaim());
        //设置描述信息
        if (applyDataDTO.getDesc() != null && "".equals(applyDataDTO.getDesc())) {
            bizData.put(SystemConstant.BIZ_DATA_DESC, applyDataDTO.getDesc());
        }
        ApplyDTO build = ApplyDTO.builder()
                .appId(applyDataDTO.getAppId())
                .signature(applyDataDTO.getSignature())
                .rand(applyDataDTO.getRand())
                .issuer(applyDataDTO.getMedicalChainDtid())
                .holder(applyDataDTO.getTransPlatformDtid())
                .pieces(1)
                .expire(System.currentTimeMillis() / 1000 + 1000000)
                .type(DTCType.TICKET.getType())
                .tdrType("10002")
                .times(0)
                .applyTypeCode(2)
                .bizData(bizData)
                .build();
        Result<DTCResponse> dtcResponseResult = reqRecordService.execReq(build, serialNumber);
        if (!"200000".equals(dtcResponseResult.getCode())) {
            throw new BussinessException("请求tdaas创建凭证失败!" + dtcResponseResult.getMessage());
        }
        ResRecord resRecord = new ResRecord();
        resRecord.setSerialNumber(serialNumber.getSerialNumber());
        return resRecord;
    }


}
