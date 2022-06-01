package com.ruoyi.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.service.domain.SbkXbporder;
import com.ruoyi.service.mapper.SbkXbporderMapper;
import com.ruoyi.service.service.ISbkXbporderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 西柏坡订单Service业务层处理
 *
 * @author lucky-ya-q
 * @date 2022-06-01
 */
@Service
public class SbkXbporderServiceImpl extends ServiceImpl<SbkXbporderMapper, SbkXbporder> implements ISbkXbporderService {
    @Autowired
    private SbkXbporderMapper sbkXbporderMapper;
}
