package com.tianji.chain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description: 申请数据授权DTO
 * @author: xionglin
 * @create: 2021-08-30 18:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDataAuthDTO {
    //@NotNull(message = "appId不能为空")
    private String appId;
    //@NotNull(message = "signature不能为空")
    private String signature;
    //@NotNull(message = "rand不能为空")
    private String rand;

    //@NotNull(message = "众药帮的Dtid不能为空")
    private String medicalChainDtid;
    @NotNull(message = "配送企业的Dtid不能为空")
    private String businessUserDtid;

    @NotNull(message = "交易平台的dtid不能为空")
    private String platformDtid;

    @Size(message = "描述信息不能大于50",max = 50)
    private String desc;
}

