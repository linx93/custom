package com.tianji.transdata.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.tianji.transdata.enums.Method;
import com.tianji.transdata.model.ReqInfoDTO;
import com.tianji.transdata.service.ReqTransDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        String method = reqInfoDTO.getMethod();
        if (Method.POST.getType().equalsIgnoreCase(method)) {
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
        } else if (Method.GET.getType().equalsIgnoreCase(method)) {
            HttpRequest get = HttpRequest.get(url);
            //请求头
            if (!headerMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                    String key = entry.getKey();
                    String value = String.valueOf(entry.getValue());
                    log.info("header:{} {}", key, value);
                    get = get.header(key, value);
                }
            }
            if (body != null) {
                Map<String,Object> map = JSONUtil.toBean(body, Map.class);
                get = get.form(map);
            }
            //这就是第三方的数据结果
            String resultJson = get.execute().body();
            return resultJson;
        } else {
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("code", -100);
            dataMap.put("detail", null);
            dataMap.put("fail", true);
            dataMap.put("success", false);
            dataMap.put("requestId", null);
            dataMap.put("message", "目前只支持POST、GET");
            dataMap.put("data", null);
            String resultJson = JSONUtil.toJsonStr(dataMap);
            log.info("resultJson:{}", resultJson);
            return resultJson;
        }
    }
}
