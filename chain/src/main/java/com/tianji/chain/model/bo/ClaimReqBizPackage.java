package com.tianji.chain.model.bo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @description:
 * @author: linx
 * @create: 2021-08-29 16:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimReqBizPackage {

    @NotBlank(message = "issuer不能为空")
    private String issuer;
    @NotBlank(message = "holder不能为空")
    private String holder;
    @NotBlank(message = "unionId不能为空")
    private String unionId;
    @NotNull(message = "expire不能为空")
    private Long expire;
    @NotNull(message = "pieces不能为空")
    private Integer pieces = 1;
    private Map<String, Object> bizData;
    @NotBlank(message = "type不能为空")
    private String type;
    private Integer times = 0;
    private String targetId;
    private String tdrType;
    public static ClaimReqBizPackage parser(String text) {
        return JSONObject.parseObject(text, new TypeReference<>(){});
    }
}
