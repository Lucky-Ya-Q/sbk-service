package com.ruoyi.service.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.SbkLibraryReaderMapper;
import com.ruoyi.service.domain.SbkLibraryReader;
import com.ruoyi.service.service.ISbkLibraryReaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 图书馆读者证Service业务层处理
 * 
 * @author lucky-ya-q
 * @date 2022-06-22
 */
@Service
public class SbkLibraryReaderServiceImpl extends ServiceImpl<SbkLibraryReaderMapper, SbkLibraryReader> implements ISbkLibraryReaderService
{
    @Autowired
    private SbkLibraryReaderMapper sbkLibraryReaderMapper;}
