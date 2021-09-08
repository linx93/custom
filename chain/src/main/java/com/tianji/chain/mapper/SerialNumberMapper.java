package com.tianji.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianji.chain.model.SerialNumber;
import org.apache.ibatis.annotations.Mapper;

/**
 * serial_number - 流水编号表
 *
 * @author linx
 */
@Mapper
public interface SerialNumberMapper extends BaseMapper<SerialNumber> {

}