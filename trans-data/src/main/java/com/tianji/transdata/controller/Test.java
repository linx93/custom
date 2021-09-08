package com.tianji.transdata.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.hxmec.sdk.DefaultClient;
import com.hxmec.sdk.HxApiException;
import com.hxmec.sdk.HxClient;
import com.hxmec.sdk.enums.SignType;
import com.hxmec.sdk.model.HxCommonRequest;
import com.hxmec.sdk.model.HxCommonResponse;
import com.hxmec.sdk.utils.HxHashMap;
import com.hxmec.sdk.utils.SignatureUtil;
import com.tianji.transdata.model.ReqInfoDTO;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 测试获取交易平台数据
 * @author: xionglin
 * @create: 2021-08-31 10:34
 */
@Slf4j
public class Test {
    /*public static void main(String[] args) throws HxApiException {
        String SERVER_URL = "http://hxapigw.heisea.cn";
        // 授权账号 [ApiKey]
        String ApiKey = "z3kmulsokwt6pmwo9l2gbbk8";
        // 授权账号 [SecretKey]
        String SecretKey = "q61w0ypo0gc6a9tsy6ma623qg5seoouf";
        //操作者名称-任意
        String OptUserNickName = "linzhix";

        String token = getToken();
        token = "Bearer " + token;
        String host = SERVER_URL;
        String uri = "/settle/api/open/v1/delv/apiTest";
        String url = host + uri;
        //请求体
        Map<String, Object> body = new HashMap<>();
        body.put("current", 1);
        body.put("size", 1000);
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        String Timestamp = sd.format(new Date());

        HashMap<String, String> signMap = new HashMap<>();
        signMap.put("ApiKey", ApiKey);
        signMap.put("SignType", SignType.SHA256.name());
        signMap.put("Timestamp", Timestamp);
        //任意随机字符串
        String nonce = "ww548sehcrrr";
        signMap.put("Nonce", nonce);
        signMap.put("RequestBody", JSONUtil.toJsonStr(body));
        String Sign = SignatureUtil.getSign(signMap, SecretKey);
        log.info("sign: {}", Sign);

        url += String.format("?Nonce=%s&ApiKey=%s&Sign=%s&Timestamp=%s&SignType=%s", nonce, ApiKey, Sign, Timestamp, SignType.SHA256.name());
        log.info("url:{}", url);
        log.info("body:{}", JSONUtil.toJsonStr(body));
        log.info("token:{}", token);
        String json = HttpRequest.post(url)
                .header("Authorization", token)
                .header("OptUserNickName", OptUserNickName)
                .body(JSONUtil.toJsonStr(body))
                .execute().body();
        log.info("response:{}", json);

        //测试我的接口
        ReqInfoDTO reqInfoDTO = new ReqInfoDTO();
        reqInfoDTO.setMethod("POST");
        reqInfoDTO.setUrl(url);
        Map<String, Object> headerMap = new HashMap<>(8);
        headerMap.put("Authorization", token);
        headerMap.put("OptUserNickName", OptUserNickName);
        reqInfoDTO.setHeaderMap(headerMap);
        reqInfoDTO.setBody(JSONUtil.toJsonStr(body));
        String body1 = HttpRequest.post("http://localhost:18001//api/v1/transData/reqTranData")
                .body(JSONUtil.toJsonStr(reqInfoDTO))
                .execute().body();
        log.info("response:{}", body1);

    }*/


    private static String getToken() throws HxApiException {
        String SERVER_URL = "http://hxapigw.heisea.cn";
        // 授权账号 [ApiKey]
        String ApiKey = "z3kmulsokwt6pmwo9l2gbbk8";
        // 授权账号 [SecretKey]
        String SecretKey = "q61w0ypo0gc6a9tsy6ma623qg5seoouf";
        //操作者名称-任意
        HxClient hxClient = new DefaultClient(SERVER_URL, SignType.SHA256.name(), "UTF-8");
        HxCommonRequest defaultRequest = new HxCommonRequest();
        HxHashMap map = new HxHashMap();
        map.put("grant_type", "client_credentials");
        map.put("client_id", ApiKey);
        map.put("client_secret", SecretKey);
        defaultRequest.setApiUrlPath("/newAuth/oauth/token");//目前固定，后续会隐藏请求的地址
        defaultRequest.setUdfParams(map);
        HxCommonResponse hxResponse = (HxCommonResponse) hxClient.execute(defaultRequest, false);
        return String.valueOf(JSONUtil.toBean(hxResponse.getData(), HashMap.class).get("access_token"));
    }
}
