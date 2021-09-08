package com.tianji.chain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * serial_number - 流水编号表
 *
 * @author linx
 */
@Getter
@Setter
public class SerialNumber {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流水编号
     */
    private String serialNumber;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 流水号类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]
     */
    private Integer type;

    /**
     * 通过这个流水号查询到一次数据就加1（+1）
     */
    private Integer number;

}
