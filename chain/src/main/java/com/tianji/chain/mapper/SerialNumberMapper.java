package com.tianji.chain.mapper;

import io.mybatis.mapper.Mapper;
import com.tianji.chain.model.SerialNumber;
/**
 * serial_number - 流水编号表
 *
 * @author linx
 */
@org.apache.ibatis.annotations.Mapper
public interface SerialNumberMapper extends Mapper<SerialNumber, Long> {

}