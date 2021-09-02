package com.tianji.transdata.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.tianji.transdata.model.ReqInfoDTO;
import com.tianji.transdata.service.ReqTransDataService;
import lombok.extern.slf4j.Slf4j;
import net.phadata.identity.utils.ByteUtil;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.Random;

/**
 * @description: 请求交易数据实现
 * @author: linx
 * @create: 2021-08-31 09:47
 */
@Slf4j
@Service
public class ReqTransDataServiceImpl implements ReqTransDataService {
    @Value("${trans-data.appId:}")
    private String appId;

    @Value("${trans-data.secret:}")
    private String secret;

    @Value("${trans-data.createClaim:}")
    private String createClaim;


    @Override
    public String reqTranData(ReqInfoDTO reqInfoDTO) {
        //请求交易平台的地址
        String url = reqInfoDTO.getUrl();
        //请求头
        Map<String, Object> headerMap = reqInfoDTO.getHeaderMap();
        //请求参数 json
        String body = reqInfoDTO.getBody();
        //执行请求
        HttpRequest post = HttpRequest.post(url);
        //设置头
        if (!headerMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                log.info("header:{} {}", key, value);
                post = post.header(key, value);
            }
        }
        if (body != null) {
            post = post.body(body);
        }
        //这就是第三方的数据结果
        String resultJson = post.execute().body();

        //请求柯博的
       /* ClaimReqBizPackage build = ClaimReqBizPackage.builder().build();
        String rand = getRand();
        String result = HttpRequest.post(createClaim)
                .header("x-key", appId)
                .header("x-signature", hmacSha256(secret, appId, rand))
                .header("x-rand", rand)
                .body(JSON.toJSONString(build))
                .execute().body();*/
        log.info("resultJson:{}", resultJson);
        return resultJson;
    }

    private String getRand() {
        int x = new Random().nextInt(899999) + 100000;
        return String.valueOf(x);
    }

    private String hmacSha256(String secret, String key, String rand) {
        String raw = "key=%s&secret=%s&rand=%s";
        String plain = String.format(raw, key, secret, rand);
        String sign = "";
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] bytes = mac.doFinal(plain.getBytes());
            sign = ByteUtil.byte2HexString(bytes);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }
}
