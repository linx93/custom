package com.tianji.chain.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 获取结果的DTO
 * @author: xionglin
 * @create: 2021-09-05 14:01
 */
@Data
public class FindResultDTO {
    @NotNull(message = "流水号serialNumber不能为空")
    private String serialNumber;
    @NotNull(message = "appId不能为空")
    private String appId;
    @NotNull(message = "signature不能为空")
    private String signature;
    @NotNull(message = "rand不能为空")
    private String rand;
}
