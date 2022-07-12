package com.ruoyi.service.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class EmsParam {
    private String sign;
    private JSONObject requestParam;
}
