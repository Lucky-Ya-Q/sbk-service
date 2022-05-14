package com.ruoyi.service.service;

import com.alibaba.fastjson.JSONObject;

public interface CSBService {
    JSONObject auth_encrypt(String signNo);

    JSONObject qrcode_channel_encrypt(String systemName);

    JSONObject qrcode_channel_query_encrypt(String qrCode);

    JSONObject qrcode_valid_encrypt(String qrCode, String busiType, String systemName);
}
