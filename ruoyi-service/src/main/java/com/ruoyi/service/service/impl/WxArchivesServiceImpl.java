package com.ruoyi.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.service.domain.WxArchives;
import com.ruoyi.service.mapper.WxArchivesMapper;
import com.ruoyi.service.service.WxArchivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxArchivesServiceImpl extends ServiceImpl<WxArchivesMapper, WxArchives> implements WxArchivesService {
    @Autowired
    private WxArchivesMapper wxArchivesMapper;

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WxArchives> selectListByLambdaQueryWrapper(LambdaQueryWrapper<WxArchives> lambdaQueryWrapper) {
        return wxArchivesMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public WxArchives selectOneByLambdaQueryWrapper(LambdaQueryWrapper<WxArchives> lambdaQueryWrapper) {
        return wxArchivesMapper.selectOne(lambdaQueryWrapper.last("limit 1"));
    }
}
