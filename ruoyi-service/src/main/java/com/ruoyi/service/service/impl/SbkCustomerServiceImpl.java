package com.ruoyi.service.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.SbkCustomerMapper;
import com.ruoyi.service.domain.SbkCustomer;
import com.ruoyi.service.service.ISbkCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 客户信息Service业务层处理
 * 
 * @author lucky-ya-q
 * @date 2022-04-28
 */
@Service
public class SbkCustomerServiceImpl extends ServiceImpl<SbkCustomerMapper, SbkCustomer> implements ISbkCustomerService
{
    @Autowired
    private SbkCustomerMapper sbkCustomerMapper;}
