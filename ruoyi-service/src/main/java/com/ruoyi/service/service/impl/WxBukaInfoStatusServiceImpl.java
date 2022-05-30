package com.ruoyi.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.service.domain.WxBukaInfoStatus;
import com.ruoyi.service.mapper.WxBukaInfoStatusMapper;
import com.ruoyi.service.service.WxBukaInfoStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxBukaInfoStatusServiceImpl extends ServiceImpl<WxBukaInfoStatusMapper, WxBukaInfoStatus> implements WxBukaInfoStatusService {
    @Autowired
    private WxBukaInfoStatusMapper wxBukaInfoStatusMapper;

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WxBukaInfoStatus> selectListByLambdaQueryWrapper(LambdaQueryWrapper<WxBukaInfoStatus> lambdaQueryWrapper) {
        return wxBukaInfoStatusMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public WxBukaInfoStatus selectOneByLambdaQueryWrapper(LambdaQueryWrapper<WxBukaInfoStatus> lambdaQueryWrapper) {
        return wxBukaInfoStatusMapper.selectOne(lambdaQueryWrapper.last("limit 1"));
    }
}
