package com.tianji.chain;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.tianji.chain.model.dto.ApplyBindDTO;
import com.tianji.chain.model.dto.ApplyDataAuthDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

/**
 * @description: 获取数据授权的测试
 * @author: xionglin
 * @create: 2021-09-05 15:26
 */
@Slf4j
@SpringBootTest
public class ApplyAuthDataTests {
    @Test
    void applyDataAuth() {
        String reqUrl = "http://192.168.1.44:18002/api/v1/chain/apply/applyDataAuth";
        String appId = "tj06f5219fd80c110a";
        String secret = "a38bd61ba2987eb572d4e56cebb65c47cb00e458e286899cca02de9f7a53803d";
        //时间戳+随机四位
        String rand = System.currentTimeMillis()+String.format("%04d",new Random().nextInt(9999));
        String signature = getSignature(appId, secret, rand);
        //医链的Dtid
        String medicalChainDtid = "dtid:dtca:3mcqJ7vxJ7XHKS66HhDjQHQT6GEh";
        //药企的Dtid
        String businessUserDtid = "dtid:dtca:bT9zYhu2M1cJAfVGeRrdD16E7ic";
        /**
         * 构建请求体参数
         */
        ApplyDataAuthDTO build = ApplyDataAuthDTO.builder()
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
    void getDataAuthResult() {
        String reqUrl = "http://192.168.0.102:18002/api/v1/chain/res/findAuthResult";
        String serialNumber = "b92a8060-c2ea-431d-852b-1c7f394c0bd9";
        String appId = "tjed1aff77132de9a6";
        String secret = "a1eda9e647f321c626a496148b19cac1b86ba7d1d9bf46b9bb8197d35ca665c1";
        //时间戳+随机四位
        String rand = System.currentTimeMillis()+String.format("%04d",new Random().nextInt(9999));
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
     * @param appId   应用id
     * @param secret  密钥
     * @param rand    随机
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
        for(int i = 0; i < bytes.length; ++i) {
            String temp = Integer.toHexString(bytes[i] & 255);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }

        return stringBuffer.toString();
    }



    @Test
    void getSignature() throws InterruptedException {
        String appId = "tj7356e3f9d9d8ed2e";
        String secret = "7761f1a69cc49c31f2bdeab26075fa188b74c6ecbb28f1b309ba086591d73318";
        //时间戳+随机四位
        String rand = System.currentTimeMillis()/1000+String.format("%04d",new Random().nextInt(9999));
        String signature = getSignature(appId, secret, rand);
        log.info("rand:{}",rand);
        log.info("signature:{}",signature);
    }
}
