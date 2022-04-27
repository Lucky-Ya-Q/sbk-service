package com.ruoyi.service.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.SbkUploadCheckMapper;
import com.ruoyi.service.domain.SbkUploadCheck;
import com.ruoyi.service.service.ISbkUploadCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 检测上传记录Service业务层处理
 * 
 * @author lucky-ya-q
 * @date 2022-04-27
 */
@Service
public class SbkUploadCheckServiceImpl extends ServiceImpl<SbkUploadCheckMapper, SbkUploadCheck> implements ISbkUploadCheckService
{
    @Autowired
    private SbkUploadCheckMapper sbkUploadCheckMapper;}
