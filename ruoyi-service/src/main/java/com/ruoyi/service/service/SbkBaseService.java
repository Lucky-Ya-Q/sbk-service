package com.ruoyi.service.service;

import com.ruoyi.service.dto.ZkjdcxParam;

import java.util.List;
import java.util.Map;

public interface SbkBaseService {
    Map<String, Object> getShenLingData(ZkjdcxParam zkjdcxParam,String state);

    List<Map<String, Object>> getBuHuanKaData(ZkjdcxParam zkjdcxParam,String state);
}
