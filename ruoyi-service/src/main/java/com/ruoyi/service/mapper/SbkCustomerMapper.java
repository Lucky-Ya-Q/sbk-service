package com.ruoyi.service.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.service.domain.SbkCustomer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 客户信息Mapper接口
 * 
 * @author lucky-ya-q
 * @date 2022-04-28
 */
public interface SbkCustomerMapper extends BaseMapper<SbkCustomer>
{
    Map<String, Integer> bukaCount(Long bukaId);
}
