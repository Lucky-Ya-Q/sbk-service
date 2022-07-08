package com.ruoyi.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.service.service.LifeEmsService;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LifeEmsServiceImpl implements LifeEmsService {
    @Autowired
    private RestTemplate restTemplate;
    private final String baseUrl = "https://211.156.197.233/jdpt-order-pus-web";

    @Override
    public JSONObject pl() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = getSignJSONObject("logistics_order_no");

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        JSONObject result = restTemplate.postForObject(baseUrl + "/interface/receive/pl", entity, JSONObject.class);
        return null;
    }

    @Override
    public JSONObject undoOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = getSignJSONObject("logistics_order_no");

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        JSONObject result = restTemplate.postForObject(baseUrl + "/interface/receive/undoOrder", entity, JSONObject.class);
        return null;
    }

    @Override
    public JSONObject query() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = getSignJSONObject("logistics_rder_no");

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        JSONObject result = restTemplate.postForObject(baseUrl + "/oms/pickupRange/query", entity, JSONObject.class);
        return null;
    }

    @Override
    public JSONObject collectResultQuery() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = getSignJSONObject("logistics_order_no");

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        JSONObject result = restTemplate.postForObject(baseUrl + "/interface/receive/collectResultQuery", entity, JSONObject.class);
        return null;
    }

    @Override
    public JSONObject updatePreTime() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = getSignJSONObject("logistics_order_no");

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        JSONObject result = restTemplate.postForObject(baseUrl + "/customer/updatePreTime", entity, JSONObject.class);
        return null;
    }

    private static JSONObject getSignJSONObject(String logistics_order_no) {
        JSONObject jsonObject = new JSONObject();
        String salt = "19dd6c1cec504ac854645f352eb03209";
        jsonObject.put("sign", HmacUtils.hmacMd5Hex(salt, logistics_order_no));
        return jsonObject;
    }

    public static void main(String[] args) {
        JSONObject signJSONObject = getSignJSONObject("1113754678634");
        System.out.println(signJSONObject.toJSONString());
    }
}
