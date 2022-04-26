package com.ruoyi.service.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.SbkScenicSpotsMapper;
import com.ruoyi.service.domain.SbkScenicSpots;
import com.ruoyi.service.service.ISbkScenicSpotsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 旅游景点Service业务层处理
 * 
 * @author lucky-ya-q
 * @date 2022-04-26
 */
@Service
public class SbkScenicSpotsServiceImpl extends ServiceImpl<SbkScenicSpotsMapper, SbkScenicSpots> implements ISbkScenicSpotsService
{
    @Autowired
    private SbkScenicSpotsMapper sbkScenicSpotsMapper;}
