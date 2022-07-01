package com.ruoyi.service.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.SbkEmsAddressMapper;
import com.ruoyi.service.domain.SbkEmsAddress;
import com.ruoyi.service.service.ISbkEmsAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 邮政地址簿Service业务层处理
 * 
 * @author lucky-ya-q
 * @date 2022-06-30
 */
@Service
public class SbkEmsAddressServiceImpl extends ServiceImpl<SbkEmsAddressMapper, SbkEmsAddress> implements ISbkEmsAddressService
{
    @Autowired
    private SbkEmsAddressMapper sbkEmsAddressMapper;}
