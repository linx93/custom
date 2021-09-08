package com.tianji.chain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Date;

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
    private String bizData;

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
    private String claim;


}