package com.ruoyi.service.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.SbkLibraryRenewMapper;
import com.ruoyi.service.domain.SbkLibraryRenew;
import com.ruoyi.service.service.ISbkLibraryRenewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 续借记录Service业务层处理
 * 
 * @author lucky-ya-q
 * @date 2022-06-23
 */
@Service
public class SbkLibraryRenewServiceImpl extends ServiceImpl<SbkLibraryRenewMapper, SbkLibraryRenew> implements ISbkLibraryRenewService
{
    @Autowired
    private SbkLibraryRenewMapper sbkLibraryRenewMapper;}
