package com.tianji.chain.model.bo;

import lombok.Data;

/**
 * @description: appId、secret、rand
 * @author: xionglin
 * @create: 2021-09-01 15:58
 */
@Data
public class AppBO {
    private String appId;
    private String rand;
    private String signature;

}
