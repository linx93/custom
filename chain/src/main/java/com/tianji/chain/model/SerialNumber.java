package com.tianji.chain.model;

import io.mybatis.provider.Entity;
import org.apache.ibatis.type.JdbcType;

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
@Entity.Table(value = "serial_number", remark = "流水编号表", autoResultMap = true)
public class SerialNumber {
    @Entity.Column(value = "id", id = true, remark = "", updatable = false, insertable = false)
    private Integer id;

    @Entity.Column(value = "serial_number", remark = "流水编号")
    private String serialNumber;

    @Entity.Column(value = "create_time", remark = "创建时间", jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    @Entity.Column(value = "type", remark = "流水号类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]")
    private Integer type;

    @Entity.Column(value = "number", remark = "通过这个流水号查询到一次数据就加1（+1）")
    private Integer number;

}
