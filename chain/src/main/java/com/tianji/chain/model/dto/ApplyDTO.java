package com.tianji.chain.model.dto;

import com.tinji.common.enums.ApplyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @description: 申请的请求VO
 * @author: xionglin
 * @create: 2021-08-29 17:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDTO {
    @NotBlank(message = "issuer不能为空")
    private String issuer;
    @NotBlank(message = "holder不能为空")
    private String holder;
    @NotNull(message = "expire不能为空")
    private Long expire;
    @NotNull(message = "pieces不能为空")
    private Integer pieces = 1;
    private Integer times = 0;
    private String tdrType = "10002";
    private Map<String, Object> bizData;
    @NotBlank(message = "type不能为空")
    private String type;

    @NotNull(message = "applyTypeCode只能为;  [1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]")
    private Integer applyTypeCode;

    @NotNull(message = "appId不能为空")
    private String appId;

    private String signature;

    private String rand;
}
