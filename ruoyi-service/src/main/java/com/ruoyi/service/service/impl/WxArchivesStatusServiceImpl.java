package com.ruoyi.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.service.domain.WxArchivesStatus;
import com.ruoyi.service.mapper.WxArchivesStatusMapper;
import com.ruoyi.service.service.WxArchivesStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxArchivesStatusServiceImpl extends ServiceImpl<WxArchivesStatusMapper, WxArchivesStatus> implements WxArchivesStatusService {
    @Autowired
    private WxArchivesStatusMapper wxArchivesStatusMapper;

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WxArchivesStatus> selectListByLambdaQueryWrapper(LambdaQueryWrapper<WxArchivesStatus> lambdaQueryWrapper) {
        return wxArchivesStatusMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public WxArchivesStatus selectOneByLambdaQueryWrapper(LambdaQueryWrapper<WxArchivesStatus> lambdaQueryWrapper) {
        return wxArchivesStatusMapper.selectOne(lambdaQueryWrapper.last("limit 1"));
    }
}
