package com.ruoyi.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.service.domain.WxArchives;
import com.ruoyi.service.mapper.WxArchivesMapper;
import com.ruoyi.service.service.WxArchivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxArchivesServiceImpl extends ServiceImpl<WxArchivesMapper, WxArchives> implements WxArchivesService {
    @Autowired
    private WxArchivesMapper wxArchivesMapper;
}
