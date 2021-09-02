package com.tianji.chain.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @description: 申请绑定数字身份DTO
 * @author: xionglin
 * @create: 2021-08-30 18:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyBindDTO {
    @NotNull(message = "appId不能为空")
    private String appId;
    @NotNull(message = "signature不能为空")
    private String signature;
    @NotNull(message = "rand不能为空")
    private String rand;

    @NotNull(message = "medicalChainDtid不能为空")
    private String medicalChainDtid;
    @NotNull(message = "businessUserDtid不能为空")
    private String businessUserDtid;

}
