package com.tianji.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianji.chain.model.ResRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * res_record - 授权记录
 *
 * @author linx
 */
@Component
@Mapper
public interface ResRecordMapper extends BaseMapper<ResRecord> {

}