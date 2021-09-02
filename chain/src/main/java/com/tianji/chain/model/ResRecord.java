package com.tianji.chain.model;

import io.mybatis.provider.Entity;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * res_record - 授权记录
 *
 * @author linx
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity.Table(value = "res_record", remark = "授权记录", autoResultMap = true)
public class ResRecord {
  @Entity.Column(value = "id", id = true, remark = "请求记录表", updatable = false, insertable = false)
  private Integer id;

  @Entity.Column(value = "issuer", remark = "签发者")
  private String issuer;

  @Entity.Column(value = "holder", remark = "持有者")
  private String holder;

  @Entity.Column(value = "expire", remark = "过期时间", jdbcType = JdbcType.TIMESTAMP)
  private ZonedDateTime expire;

  @Entity.Column(value = "type", remark = "凭证类型")
  private String type;

  @Entity.Column(value = "times", remark = "转让次数")
  private Integer times;

  @Entity.Column(value = "biz_data", remark = "数据载体")
  private String bizData;

  @Entity.Column(value = "serial_number", remark = "流水编号")
  private String serialNumber;

  @Entity.Column(value = "res_type", remark = "请求类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]")
  private String resType;

  @Entity.Column(value = "res_time", remark = "请求时间", jdbcType = JdbcType.TIMESTAMP)
  private Date resTime;

  @Entity.Column(value = "result", remark = "结果 success/fail")
  private String result;

  @Entity.Column(value = "claim", remark = "完整的凭证数据")
  private String claim;


}
