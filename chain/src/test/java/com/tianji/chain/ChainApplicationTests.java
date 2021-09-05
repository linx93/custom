package com.tianji.chain;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hxmec.sdk.DefaultClient;
import com.hxmec.sdk.HxApiException;
import com.hxmec.sdk.HxClient;
import com.hxmec.sdk.enums.SignType;
import com.hxmec.sdk.model.HxCommonRequest;
import com.hxmec.sdk.model.HxCommonResponse;
import com.hxmec.sdk.utils.HxHashMap;
import com.hxmec.sdk.utils.SignatureUtil;
import com.tianji.chain.model.dto.ApplyDataDTO;
import com.tianji.chain.model.dto.ReqInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class ChainApplicationTests {

    /**
     * 名词解释:
     * [dtid:数字身份]
     * []
     * []
     * []
     * []
     */
    /**
     * 获取数据[结算账户]
     */
    @Test
    void getApplyDataResult() {
        String reqUrl = "http://192.168.1.44:18002/api/v1/chain/res/findDataResult";
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

    /**
     * 申请查询结算账户测试[这里是拿查询结算账户作为例子，其他的接口也一样，无非就是uri、请求参数这些东西不一样]
     *
     * @throws HxApiException
     */
    @Test
    void applySettleAccount() throws HxApiException {
        String reqUrl = "http://192.168.1.44:18002/api/v1/chain/apply/applyData";
        //请求体
        Map<String, Object> bodyMap = new HashMap<>(8);
        //bodyMap.put("current",1);
        //bodyMap.put("size",1000);
        //医疗的dtid
        String medicalChainDtid = "";
        //交易平台的dtid
        String transPlatformDtid = "";
        //流水号[这个流水号是数据授权结果记录中的流水号]
        String serialNumber = "";
        String OptUserNickName = "linzhix";
        Map<String, Object> headerMap = new HashMap<>(8);
        headerMap.put("Authorization", getToken());
        headerMap.put("OptUserNickName", OptUserNickName);
        String appId = "tjed1aff77132de9a6";
        String secret = "a1eda9e647f321c626a496148b19cac1b86ba7d1d9bf46b9bb8197d35ca665c1";
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        String rand = sd.format(new Date());
        String signature = getSignature(appId, secret, rand);
        ApplyDataDTO applyDataDTO = getReqParam("/api/open/v1/delv/settleAccount/query",
                bodyMap,
                headerMap,
                appId,
                rand,
                signature,
                serialNumber,//流水号
                medicalChainDtid,
                transPlatformDtid);
        String json = HttpRequest.post(reqUrl)
                .body(JSONUtil.toJsonStr(applyDataDTO))
                .execute().body();
        //json里面会返回一个流水号[serialNumber],用来查询数据
        log.info("response:{}", json);
    }


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

    /**
     * 构建请求参数
     *
     * @param uri               交易数据平台文档给的各个uri
     * @param body              交易数据平台文档给的请求体参数[map->jsonString]
     * @param headerMap         交易数据平台文档给的请求头map
     * @param appId             天机给的appId
     * @param rand
     * @param signature         签名
     * @param serialNumber      流水号，由之前的接口给的
     * @param medicalChainDtid  医链的dtid
     * @param transPlatformDtid 交易数据平台的dtid
     * @return
     * @throws HxApiException
     */
    private ApplyDataDTO getReqParam(String uri,
                                     Map<String, Object> body,
                                     Map<String, Object> headerMap,
                                     String appId,
                                     String rand,
                                     String signature,
                                     String serialNumber,
                                     String medicalChainDtid,
                                     String transPlatformDtid) throws HxApiException {
        ReqInfoDTO reqInfoDTO = new ReqInfoDTO();
        reqInfoDTO.setUrl(getUrl(uri, body));
        reqInfoDTO.setBody(JSON.toJSONString(body));
        //默认是POST，目前只支持POST、GET
        reqInfoDTO.setMethod("POST");
        reqInfoDTO.setHeaderMap(headerMap);
        ApplyDataDTO applyDataDTO = ApplyDataDTO.builder()
                .reqInfoDTO(reqInfoDTO)
                .appId(appId)
                .rand(rand)
                .signature(signature)
                .serialNumber(serialNumber)
                .medicalChainDtid(medicalChainDtid)
                .transPlatformDtid(transPlatformDtid)
                .build();
        return applyDataDTO;
    }

    /**
     * 构建请求交易平台完整的url[这个是根据交易平台提供的接口文档中的demo得来的]
     *
     * @param uri  交易平台提供的接口文档中的uri
     * @param body 请求体[按交易平台接口文档传]
     * @return
     */
    private String getUrl(String uri, Map<String, Object> body) {
        String SERVER_URL = "http://hxapigw.heisea.cn";
        // 授权账号 [ApiKey]
        String ApiKey = "z3kmulsokwt6pmwo9l2gbbk8";
        // 授权账号 [SecretKey]
        String SecretKey = "q61w0ypo0gc6a9tsy6ma623qg5seoouf";
        //操作者名称-任意
        String host = SERVER_URL;
        uri = uri == null ? "/settle/api/open/v1/delv/apiTest" : uri;
        String url = host + uri;
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        String Timestamp = sd.format(new Date());
        HashMap<String, String> signMap = new HashMap<>();
        signMap.put("ApiKey", ApiKey);
        signMap.put("SignType", SignType.SHA256.name());
        signMap.put("Timestamp", Timestamp);
        //任意随机字符串
        String nonce = "ww548sehcrrr";
        signMap.put("Nonce", nonce);
        signMap.put("RequestBody", JSON.toJSONString(body));
        String Sign = SignatureUtil.getSign(signMap, SecretKey);
        //log.info("sign: {}", Sign);
        url += String.format("?Nonce=%s&ApiKey=%s&Sign=%s&Timestamp=%s&SignType=%s", nonce, ApiKey, Sign, Timestamp, SignType.SHA256.name());
        log.info("url:{}", url);
        return url;
    }

    /**
     * 这个就是交易平台提供的接口文档中的获取token
     *
     * @return
     * @throws HxApiException
     */
    private String getToken() throws HxApiException {
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
        return String.valueOf(JSON.parseObject(hxResponse.getData(), HashMap.class).get("access_token"));
    }
}
