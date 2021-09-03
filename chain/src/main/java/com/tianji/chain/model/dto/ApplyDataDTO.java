package com.tianji.chain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @description: 获取数据DTO
 * @author: xionglin
 * @create: 2021-08-30 18:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyDataDTO {
    @NotNull(message = "appId不能为空")
    private String appId;
    @NotNull(message = "signature不能为空")
    private String signature;
    @NotNull(message = "rand不能为空")
    private String rand;


    @NotNull(message = "医链的dtid不能为空")
    private String medicalChainDtid;
    @NotNull(message = "交易平台的dtid不能为空")
    private String transPlatformDtid;

    @NotNull(message = "serialNumber不能为空")
    private String serialNumber;

    private ReqInfoDTO reqInfoDTO;


}
