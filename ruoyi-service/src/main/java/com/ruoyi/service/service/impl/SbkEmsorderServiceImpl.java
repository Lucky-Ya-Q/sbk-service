package com.ruoyi.service.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.SbkEmsorderMapper;
import com.ruoyi.service.domain.SbkEmsorder;
import com.ruoyi.service.service.ISbkEmsorderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 邮政订单Service业务层处理
 * 
 * @author lucky-ya-q
 * @date 2022-07-01
 */
@Service
public class SbkEmsorderServiceImpl extends ServiceImpl<SbkEmsorderMapper, SbkEmsorder> implements ISbkEmsorderService
{
    @Autowired
    private SbkEmsorderMapper sbkEmsorderMapper;}
