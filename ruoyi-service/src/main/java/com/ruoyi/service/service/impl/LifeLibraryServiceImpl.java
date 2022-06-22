package com.ruoyi.service.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.service.LifeLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LifeLibraryServiceImpl implements LifeLibraryService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisCache redisCache;
    private final String baseUrl = "http://123.182.227.68:8889/openlib";

    @Override
    public String token() {
        String token = redisCache.getCacheObject("life:library:token");
        if (StrUtil.isNotEmpty(token)) {
            log.info("缓存中获取token");
            return token;
        }
        String result = restTemplate.getForObject(baseUrl + "/service/barcode/token?appid=sjzsocial&secret=tzs123tsgfad1235afsjzsocialt6ikdg", String.class);
        token = result2Json(result).getJSONArray("messagelist").getJSONObject(0).getString("token");
        redisCache.setCacheObject("life:library:token", token, 1, TimeUnit.DAYS);
        log.info("接口中获取token");
        return token;
    }

    @Override
    public void addreader() {

    }

    @Override
    public void rdloanlist() {

    }

    @Override
    public void renewbook() {

    }

    @Override
    public JSONObject searchreaderlist(String rdcertify) {
        String url = StrUtil.format(
                "{}/service/reader/searchreaderlist?token={}&selecttype=rdcertify&queryvalue={}",
                baseUrl, token(), rdcertify);
        String result = restTemplate.getForObject(url, String.class);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject search(String q, String searchWay, String page, String rows) {
        String url = StrUtil.format(
                "http://www.sjzlib.cn:8088/opac/advance/search?q={}&searchWay={}&return_fmt=json&view=json&page={}&rows={}",
                q, searchWay, page, rows
        );
        return restTemplate.getForObject(url, JSONObject.class);
    }

    @Override
    public JSONObject holding(String bookrecno) {
        return restTemplate.getForObject("http://www.sjzlib.cn:8088/opac/api/holding/" + bookrecno, JSONObject.class);
    }

    @Override
    public JSONObject book(String bookrecno) {
        return restTemplate.getForObject("http://www.sjzlib.cn:8088/opac/api/book/" + bookrecno, JSONObject.class);
    }

    private JSONObject result2Json(String result) {
        JSONObject jsonObject = JSON.parseObject(result);
        if (!jsonObject.getBoolean("success")) {
            String message = jsonObject.getJSONArray("messagelist").getJSONObject(0).getString("message");
            throw new ServiceException(message);
        }
        return jsonObject;
    }
}
