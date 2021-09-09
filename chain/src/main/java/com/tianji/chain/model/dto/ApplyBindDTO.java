package com.tianji.chain.model.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull(message = "医链的Dtid不能为空")
    private String medicalChainDtid;
    @NotNull(message = "药企的Dtid不能为空")
    private String businessUserDtid;

    @Size(max = 50,message = "描述信息不大于50")
    private String desc;
}
