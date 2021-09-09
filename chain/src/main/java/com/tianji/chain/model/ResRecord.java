package com.tianji.chain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tianji.chain.config.JsonTypeHandler;
import lombok.*;
import net.phadata.identity.dtc.entity.VerifiableClaim;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

/**
 * res_record 响应记录表
 *
 * @author linx
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class ResRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 签发者
     */
    private String issuer;

    /**
     * 持有者
     */
    private String holder;

    /**
     * 过期时间
     */
    private ZonedDateTime expire;

    /**
     * 凭证类型
     */
    private String type;

    /**
     * 转让次数
     */
    private Integer times;

    /**
     * 数据载体
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    @JSONField(serialize = false)
    private Map<String, Object> bizData;

    /**
     * 流水编号
     */
    private String serialNumber;

    /**
     * 请求类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]
     */
    private String resType;

    /**
     * 请求时间
     */
    private Date resTime;

    /**
     * 结果 success/fail
     */
    private String result;

    /**
     * 完整的凭证数据
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private VerifiableClaim claim;


}