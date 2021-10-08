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
import java.util.*;

@Slf4j
@SpringBootTest
class TransDataApplicationTests {

    @Test
    void contextLoads() {

        LinkedHashMap map = new LinkedHashMap(8, 0.75f, true);
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);
        map.forEach((k, v) -> {
            System.out.print(k);
            System.out.println(v);
        });
        map.get("a");
        map.get("b");
        System.out.println("--------");
        map.forEach((k, v) -> {
            System.out.print(k);
            System.out.println(v);
        });
    }


}
