package com.tianji.chain.service;

import com.tianji.chain.model.ResRecord;
import com.tianji.chain.model.dto.ApplyBindDTO;
import com.tianji.chain.model.dto.ApplyDTO;
import com.tianji.chain.model.dto.ApplyDataAuthDTO;
import com.tianji.chain.model.dto.ApplyDataDTO;

/**
 * @description: 请求申请的业务处理
 * @author: xionglin
 * @create: 2021-08-29 18:06
 */
public interface ApplyService {
    /**
     * d
     * @param applyDTO
     * @param type [(1,"申请数据授权"),(2,"获取数据"),(3,"申请绑定数字身份")]
     * @return
     */
    @Deprecated
    ResRecord executeApply(ApplyDTO applyDTO,Integer type);


    /**
     * 申请绑定数字身份
     * @param applyBindDTO
     * @return
     */
    ResRecord applyBind(ApplyBindDTO applyBindDTO);

    /**
     * 申请获取数据的授权
     * @param applyDataAuthDTO
     * @return
     */
    ResRecord applyDataAuth(ApplyDataAuthDTO applyDataAuthDTO);

    /**
     * 申请获取数据
     * @param applyDataDTO
     * @return
     */
    ResRecord applyData(ApplyDataDTO applyDataDTO);


    /**
     * 同步的
     * @param applyDataDTO
     * @return
     */
    ResRecord applyDataSync(ApplyDataDTO applyDataDTO);

}
