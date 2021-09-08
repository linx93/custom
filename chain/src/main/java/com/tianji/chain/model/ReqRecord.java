package com.tianji.chain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * req_record -
 *
 * @author linx
 */
@Getter
@Setter
public class ReqRecord {

  @TableId(value = "grant_id", type = IdType.AUTO)
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
   * 唯一id
   */
  private String unionId;

  /**
   * 过期时间
   */
  private Long expire;

  /**
   * 凭证数
   */
  private String pieces;

  /**
   * 凭证类型
   */
  private String type;

  /**
   * 转让次数
   */
  private Integer times;

  /**
   * 用于指定发送mq队列的
   */
  private String targetId;

  /**
   * 标记tdr的类型
   */
  private String tdrType;

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
  private String reqType;

  /**
   *   请求时间
   */
  private Date reqTime;

}
