package com.ruoyi.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.domain.WxArchivesStatus;
import com.ruoyi.service.domain.WxBukaInfoStatus;

import java.util.List;

public interface WxBukaInfoStatusService extends IService<WxBukaInfoStatus> {
    List<WxBukaInfoStatus> selectListByLambdaQueryWrapper(LambdaQueryWrapper<WxBukaInfoStatus> lambdaQueryWrapper);

    WxBukaInfoStatus selectOneByLambdaQueryWrapper(LambdaQueryWrapper<WxBukaInfoStatus> lambdaQueryWrapper);
}
