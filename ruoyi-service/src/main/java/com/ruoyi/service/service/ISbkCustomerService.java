package com.ruoyi.service.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.service.domain.SbkCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 客户信息Service接口
 * 
 * @author lucky-ya-q
 * @date 2022-04-28
 */
public interface ISbkCustomerService extends IService<SbkCustomer>
{
    Map<String, Integer> bukaCount(Long bukaId);
}
