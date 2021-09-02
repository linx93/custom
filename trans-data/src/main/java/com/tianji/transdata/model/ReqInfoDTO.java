package com.tianji.transdata.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @description: 医链请求构建的请求信息，包括他们的请求地址、头、参数等
 * @author: xionglin
 * @create: 2021-08-30 22:00
 */
@Data
public class ReqInfoDTO {
    /**
     * [POST或GET]
     */
    private String requestMethod = "POST";
    /**
     * 给的demo示例中完整的url
     */
    @NotNull(message = "url不能为空")
    private String url;

    /**
     * {"Authorization":"token","OptUserNickName":"linzhix"}
     */
    private Map<String, Object> headerMap;

    /**
     * json str
     */
    private String body;

}
