package com.ruoyi.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.domain.WxArchivesStatus;
import com.ruoyi.service.domain.WxBukaInfo;

import java.util.List;

public interface WxBukaInfoService extends IService<WxBukaInfo> {
    List<WxBukaInfo> selectListByLambdaQueryWrapper(LambdaQueryWrapper<WxBukaInfo> lambdaQueryWrapper);

    WxBukaInfo selectOneByLambdaQueryWrapper(LambdaQueryWrapper<WxBukaInfo> lambdaQueryWrapper);
}
