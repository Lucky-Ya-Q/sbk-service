package com.ruoyi.service.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.SbkUploadBukaMapper;
import com.ruoyi.service.domain.SbkUploadBuka;
import com.ruoyi.service.service.ISbkUploadBukaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 补卡上传记录Service业务层处理
 * 
 * @author lucky-ya-q
 * @date 2022-04-27
 */
@Service
public class SbkUploadBukaServiceImpl extends ServiceImpl<SbkUploadBukaMapper, SbkUploadBuka> implements ISbkUploadBukaService
{
    @Autowired
    private SbkUploadBukaMapper sbkUploadBukaMapper;}
