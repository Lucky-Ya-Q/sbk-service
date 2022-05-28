package com.ruoyi.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.domain.WxArchives;

import java.util.List;

public interface WxArchivesService extends IService<WxArchives> {
    List<WxArchives> selectListByLambdaQueryWrapper(LambdaQueryWrapper<WxArchives> lambdaQueryWrapper);

    WxArchives selectOneByLambdaQueryWrapper(LambdaQueryWrapper<WxArchives> lambdaQueryWrapper);
}
