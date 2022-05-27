package com.ruoyi.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.domain.WxSmspersonEms;

public interface WxSmspersonEmsService extends IService<WxSmspersonEms> {
    JSONObject selectMailInfoBySfzh(String sfzh);
}
