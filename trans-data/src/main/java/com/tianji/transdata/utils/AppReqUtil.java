package com.tianji.transdata.utils;

import net.phadata.identity.utils.ByteUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

/**
 * @description: 构建请求saas的参数的工具类
 * @author: xionglin
 * @create: 2021-09-02 15:48
 */
public class AppReqUtil {
    private AppReqUtil() {
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
