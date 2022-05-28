package com.ruoyi.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.domain.WxArchives;
import com.ruoyi.service.domain.WxArchivesStatus;

import java.util.List;

public interface WxArchivesStatusService extends IService<WxArchivesStatus> {
    List<WxArchivesStatus> selectListByLambdaQueryWrapper(LambdaQueryWrapper<WxArchivesStatus> lambdaQueryWrapper);

    WxArchivesStatus selectOneByLambdaQueryWrapper(LambdaQueryWrapper<WxArchivesStatus> lambdaQueryWrapper);
}
