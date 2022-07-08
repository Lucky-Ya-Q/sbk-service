package com.ruoyi.service.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.service.domain.SbkLibraryReader;

public interface LifeEmsService {
    JSONObject pl(); // 上门取件
    JSONObject undoOrder(); // 取消上门取件
    JSONObject query(); // 揽收范围判断
    JSONObject collectResultQuery(); // 揽收结果查询
    JSONObject updatePreTime(); // 修改预约时间
}
