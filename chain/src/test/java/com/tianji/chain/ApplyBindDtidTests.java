package com.tianji.chain;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.tianji.chain.model.dto.ApplyBindDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 绑定dtid测试
 * @author: xionglin
 * @create: 2021-09-05 15:25
 */
@Slf4j
@SpringBootTest
public class ApplyBindDtidTests {
    @Test
    void applyBindDtid() {
        String reqUrl = "http://192.168.1.44:18002/api/v1/chain/apply/applyBind";
        String appId = "tjed1aff77132de9a6";
        String secret = "a1eda9e647f321c626a496148b19cac1b86ba7d1d9bf46b9bb8197d35ca665c1";
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        String rand = sd.format(new Date());
        String signature = getSignature(appId, secret, rand);
        //医链的Dtid
        String medicalChainDtid = "";
        //药企的Dtid
        String businessUserDtid = "";
        /**
         * 构建请求体参数
         */
        ApplyBindDTO build = ApplyBindDTO.builder()
                .appId(appId)
                .rand(rand)
                .signature(signature)
                .medicalChainDtid(medicalChainDtid)
                .businessUserDtid(businessUserDtid)
                .build();
        String json = HttpRequest.post(reqUrl)
                .body(JSONUtil.toJsonStr(build))
                .execute().body();
        //json里面会返回一个流水号[serialNumber],用来查询数据
        log.info("response:{}", json);
    }

    @Test
    void getBindDtidResult() {
        String reqUrl = "http://192.168.1.44:18002/api/v1/chain/res/findBindResult";
        String serialNumber = "";
        String appId = "tjed1aff77132de9a6";
        String secret = "a1eda9e647f321c626a496148b19cac1b86ba7d1d9bf46b9bb8197d35ca665c1";
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        String rand = sd.format(new Date());
        String signature = getSignature(appId, secret, rand);
        HttpResponse httpResponse = HttpRequest.get(reqUrl)
                .form("appId", appId)
                .form("rand", rand)
                .form("signature", signature)
                .form("serialNumber", serialNumber)
                .execute();
        //boolean ok = httpResponse.isOk();
        String body = httpResponse.body();
        //body包含了交易平台的数据[比如结算账户信息]
        log.info("resultData:{}", body);
    }


/**********************************************************°°°°°°°°*************************************/
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
            SecretKeySpec secretKeySpec = new SecretKeySpec(appId.getBytes(), "HmacSHA256");
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
        String temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
