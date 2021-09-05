package com.tianji.transdata;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hxmec.sdk.DefaultClient;
import com.hxmec.sdk.HxApiException;
import com.hxmec.sdk.HxClient;
import com.hxmec.sdk.enums.SignType;
import com.hxmec.sdk.model.HxCommonRequest;
import com.hxmec.sdk.model.HxCommonResponse;
import com.hxmec.sdk.utils.HttpClientUtils;
import com.hxmec.sdk.utils.HxHashMap;
import com.hxmec.sdk.utils.SignatureUtil;
import com.tianji.transdata.model.ReqInfoDTO;
import lombok.extern.slf4j.Slf4j;
import net.phadata.identity.utils.ByteUtil;
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
class TransDataApplicationTests {

    @Test
    void contextLoads() {
    }




}
