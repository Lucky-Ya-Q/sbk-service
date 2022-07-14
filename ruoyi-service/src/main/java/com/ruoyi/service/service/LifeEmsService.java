package com.ruoyi.service.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.service.domain.SbkEmsorder;
import com.ruoyi.service.domain.SbkLibraryReader;
import com.ruoyi.service.dto.LogisticsInterface;
import com.ruoyi.service.dto.MyOrderParam;
import com.ruoyi.service.dto.PlParam;
import com.ruoyi.service.dto.UndoOrderParam;

import java.util.List;

public interface LifeEmsService {
    JSONObject pl(PlParam plParam); // 上门取件
    JSONObject undoOrder(UndoOrderParam undoOrderParam); // 取消上门取件
    JSONObject query(); // 揽收范围判断
    JSONObject collectResultQuery(String txLogisticID); // 揽收结果查询
    JSONObject updatePreTime(); // 修改预约时间

    List<SbkEmsorder> myOrder(MyOrderParam myOrderParam); // 我的订单

    JSONObject querytrace(String waybillNo);

    JSONObject estimatepostage(LogisticsInterface logisticsInterface);
}
