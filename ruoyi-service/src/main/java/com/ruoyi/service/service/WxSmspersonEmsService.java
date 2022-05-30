package com.ruoyi.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.domain.WxSmspersonEms;

import java.util.List;

public interface WxSmspersonEmsService extends IService<WxSmspersonEms> {
    List<WxSmspersonEms> selectListByLambdaQueryWrapper(LambdaQueryWrapper<WxSmspersonEms> lambdaQueryWrapper);

    WxSmspersonEms selectOneByLambdaQueryWrapper(LambdaQueryWrapper<WxSmspersonEms> lambdaQueryWrapper);

    JSONObject selectMailInfoByWldh(String wldh);

    JSONObject selectMailInfoBySfzh(String sfzh);
}
