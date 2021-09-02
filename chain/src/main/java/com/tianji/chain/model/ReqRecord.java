package com.tianji.chain.model;

import io.mybatis.provider.Entity;
import org.apache.ibatis.type.JdbcType;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * req_record - 授权记录
 *
 * @author linx
 */
@Getter
@Setter
@Entity.Table(value = "req_record", remark = "授权记录", autoResultMap = true)
public class ReqRecord {
  @Entity.Column(value = "id", id = true, remark = "请求记录表", updatable = false, insertable = false)
  private Integer id;

  @Entity.Column(value = "issuer", remark = "签发者")
  private String issuer;

  @Entity.Column(value = "holder", remark = "持有者")
  private String holder;

  @Entity.Column(value = "union_id", remark = "唯一id")
  private String unionId;

  @Entity.Column(value = "expire", remark = "过期时间")
  private Long expire;

  @Entity.Column(value = "pieces", remark = "凭证数")
  private String pieces;

  @Entity.Column(value = "type", remark = "凭证类型")
  private String type;

  @Entity.Column(value = "times", remark = "转让次数")
  private Integer times;

  @Entity.Column(value = "target_id", remark = "用于指定发送mq队列的")
  private String targetId;

  @Entity.Column(value = "tdr_type", remark = "标记tdr的类型")
  private String tdrType;

  @Entity.Column(value = "biz_data", remark = "数据载体")
  private String bizData;

  @Entity.Column(value = "serial_number", remark = "流水编号")
  private String serialNumber;

  @Entity.Column(value = "req_type", remark = "请求类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]")
  private String reqType;

  @Entity.Column(value = "req_time", remark = "请求时间", jdbcType = JdbcType.TIMESTAMP)
  private Date reqTime;

}
