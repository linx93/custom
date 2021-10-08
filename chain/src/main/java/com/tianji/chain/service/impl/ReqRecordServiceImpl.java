package com.tianji.chain.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianji.chain.constant.SystemConstant;
import com.tianji.chain.enums.ApplyType;
import com.tianji.chain.exception.BussinessException;
import com.tianji.chain.model.SerialNumber;
import com.tianji.chain.model.bo.ClaimReqBizPackage;
import com.tianji.chain.model.bo.DTCResponse;
import com.tianji.chain.model.dto.ApplyDTO;
import com.tianji.chain.utils.HttpClientUtil;
import com.tianji.chain.utils.Result;

import com.tianji.chain.service.ReqRecordService;
import com.tianji.chain.mapper.ReqRecordMapper;
import com.tianji.chain.model.ReqRecord;
import com.tianji.chain.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * req_record -
 *
 * @author linx
 */
@Slf4j
@Service
public class ReqRecordServiceImpl extends ServiceImpl<ReqRecordMapper, ReqRecord> implements ReqRecordService {

    @Value("${saas.chain.createClaim:}")
    private String createClaim;

    @Value("${saas.chain.secret:}")
    private String secret;

    @Value("${saas.chain.appId:}")
    private String appKey;


    @Override
    public Result<DTCResponse> execReq(ApplyDTO applyDTO, SerialNumber serialNumber) {
        //TODO 可以做请求req_record表的持久化，暂时没
        //调用柯博的createClaim接口
        String params = buildParams(applyDTO, serialNumber);
        Header[] heads = getHeads(applyDTO.getSignature(), applyDTO.getAppId(), applyDTO.getRand());
        Result<DTCResponse> dtcResponseResult = HttpClientUtil.postForObject(createClaim, params, DTCResponse.class, heads);
        log.info("start apply create claim");
        log.info("url:{}", createClaim);
        log.info("params:{}", params);
        log.info("heads:" + Arrays.toString(heads));
        log.info("end apply create claim");
        return dtcResponseResult;
    }

    private String buildParams(ApplyDTO applyDTO, SerialNumber serialNumber) {
        ClaimReqBizPackage.ClaimReqBizPackageBuilder builder = ClaimReqBizPackage.builder();
        Integer applyTypeCode = applyDTO.getApplyTypeCode();
        if (ApplyType.APPLY_DATA_AUTH.getCode().equals(applyTypeCode)) {
            builder.targetId("grant");
            builder.number(1);
        } else if (ApplyType.OBTAIN_DATA.getCode().equals(applyTypeCode)) {
            builder.targetId("grant");
            builder.number(2);
        } else if (ApplyType.APPLY_BIND_DTID.getCode().equals(applyTypeCode)) {
            builder.targetId("grant");
            builder.number(1);
        } else {
            throw new BussinessException("applyTypeCode参数有误,只能传[1,2,3]其中一个值");
        }

        ClaimReqBizPackage build = builder
                .holder(applyDTO.getHolder())
                .issuer(applyDTO.getIssuer())
                .unionId(UUID.randomUUID().toString())
                .expire(applyDTO.getExpire())
                .pieces(applyDTO.getPieces())
                .type(applyDTO.getType())
                .bizData(buildBizData(applyDTO, serialNumber))
                .times(applyDTO.getTimes())
                .tdrType(applyDTO.getTdrType())
                .build();
        log.info("claimReqBizPackage:{}", JSON.toJSONString(build, true));
        return JSON.toJSONString(build);
    }

    private Header[] getHeads(String signature, String appId, String rand) {
        //1. 处理如果signature、appId、rand都为空的时候给默认处理
        if (StrUtil.isAllEmpty(new String[]{signature, appId, rand})) {
            appId = appKey;
            rand = System.currentTimeMillis() / 1000 + String.format("%04d", new Random().nextInt(9999));
            signature = getSignature(appId, secret, rand);
            log.info("rand:{}", rand);
            log.info("signature:{}", signature);
        }
        BasicHeader xKey = new BasicHeader("x-key", appId);
        BasicHeader xRand = new BasicHeader("x-rand", rand);
        BasicHeader xSignature = new BasicHeader("x-signature", signature);
        Header[] headers = new Header[3];
        headers[0] = xKey;
        headers[1] = xRand;
        headers[2] = xSignature;
        return headers;
    }

    private Map<String, Object> buildBizData(ApplyDTO applyDTO, SerialNumber serialNumber) {
        Map<String, Object> bizData = new HashMap<>(16);
        Map<String, Object> reqBizData = applyDTO.getBizData();
        if (reqBizData != null) {
            bizData = reqBizData;
        }
        /**
         * c类型
         */
        if (bizData.get(SystemConstant.BIZ_DATA_TYPE) == null) {
            bizData.put(SystemConstant.BIZ_DATA_TYPE, applyDTO.getApplyTypeCode());
        }
        /**
         * c类型
         */
        if (bizData.get("type") == null) {
            bizData.put("type", "-1");
        }
        /**
         * 描述
         */
        if (bizData.get(SystemConstant.BIZ_DATA_DESC) == null) {
            bizData.put(SystemConstant.BIZ_DATA_DESC, getDescOrTitle(applyDTO.getApplyTypeCode(), false));
        }
        /**
         * 标题
         */
        if (bizData.get(SystemConstant.BIZ_DATA_TITLE) == null) {
            bizData.put(SystemConstant.BIZ_DATA_TITLE, getDescOrTitle(applyDTO.getApplyTypeCode(), true));
        }
        /**
         * 流水编号
         */
        if (bizData.get(SystemConstant.SERIAL_NUMBER) == null) {
            bizData.put(SystemConstant.SERIAL_NUMBER, serialNumber.getSerialNumber());
        }
        return bizData;
    }

    private String getDescOrTitle(Integer applyTypeCode, boolean isTitle) {
        switch (applyTypeCode) {
            case 1:
                return ApplyType.APPLY_DATA_AUTH.getMessage();
            case 2:
                return ApplyType.OBTAIN_DATA.getMessage();
            case 3:
                return ApplyType.APPLY_BIND_DTID.getMessage();
            default:
                if (isTitle) {
                    return "title不明确";
                } else {
                    return "desc不明确";
                }
        }
    }

    /**
     * 构建签名
     *
     * @param appId  应用id
     * @param secret 密钥
     * @param rand   随机
     * @return
     */
    private String getSignature(String appId, String secret, String rand) {
        String raw = "key=%s&secret=%s&rand=%s";
        String plain = String.format(raw, appId, secret, rand);
        String sign = "";
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] bytes = mac.doFinal(plain.getBytes());
            sign = byte2HexString(bytes);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param bytes
     * @return
     */
    private String byte2HexString(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            String temp = Integer.toHexString(bytes[i] & 255);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }

        return stringBuffer.toString();
    }
}
