package com.ruoyi.service.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.domain.SbkLibraryReader;
import com.ruoyi.service.domain.SbkLibraryRenew;
import com.ruoyi.service.service.ISbkLibraryReaderService;
import com.ruoyi.service.service.ISbkLibraryRenewService;
import com.ruoyi.service.service.LifeLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LifeLibraryServiceImpl implements LifeLibraryService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISbkLibraryReaderService sbkLibraryReaderService;
    @Autowired
    private ISbkLibraryRenewService sbkLibraryRenewService;
    private final String baseUrl = "http://10.36.2.8:9007/8889/openlib";
    private final String baseUrl1 = "http://10.36.2.8:9007/8088/opac";

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
    public JSONObject addreader(SbkLibraryReader sbkLibraryReader) {
        sbkLibraryReader.setOperator("shbk");
        sbkLibraryReader.setCreateTime(new Date());
        String url = StrUtil.format(
                "{}/service/reader/addreader?token={}&rdcfstate=1&rdid={}&rdname={}&rdpasswd={}&rdcertify={}&operator={}&rdlib={}&rdtype={}&rdloginid={}&rdemail={}&rdsex={}&rdaddress={}&rdsort2={}",
                baseUrl, token(),
                sbkLibraryReader.getRdcertify(),
                sbkLibraryReader.getRdname(),
                sbkLibraryReader.getRdpasswd(),
                sbkLibraryReader.getRdcertify(),
                sbkLibraryReader.getOperator(),
                sbkLibraryReader.getRdlib(),
                sbkLibraryReader.getRdtype(),
                sbkLibraryReader.getRdloginid(),
                sbkLibraryReader.getRdemail(),
                sbkLibraryReader.getRdsex(),
                sbkLibraryReader.getRdaddress(),
                sbkLibraryReader.getRdsort2());
        String result = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = result2Json(result);
        String code = jsonObject.getJSONArray("messagelist").getJSONObject(0).getString("code");
        if (code.equals("R00095")) {
            sbkLibraryReaderService.save(sbkLibraryReader);
        }
        return jsonObject;
    }

    @Override
    public JSONObject rdloanlist(String rdid) {
        String url = StrUtil.format(
                "{}/service/barcode/rdloanlist?token={}&rdid={}",
                baseUrl, token(), rdid);
        String result = restTemplate.getForObject(url, String.class);
        return result2Json(result);
    }

    @Override
    public JSONObject renewbook(SbkLibraryRenew sbkLibraryRenew) {
        sbkLibraryRenew.setCreateTime(new Date());
        String url = StrUtil.format(
                "{}/service/barcode/renewbook?token={}&rdid={}&barcode={}&opuser=shbk",
                baseUrl, token(), sbkLibraryRenew.getRdid(), sbkLibraryRenew.getBarcode());
        String result = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = result2Json(result);
        String code = jsonObject.getJSONArray("messagelist").getJSONObject(0).getString("code");
        if (code.equals("R00108")) {
            sbkLibraryRenewService.save(sbkLibraryRenew);
        }
        return jsonObject;
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
                baseUrl1 + "/advance/search?q={}&searchWay={}&return_fmt=json&view=json&page={}&rows={}",
                q, searchWay, page, rows
        );
        return restTemplate.getForObject(url, JSONObject.class);
    }

    @Override
    public JSONObject holding(String bookrecno) {
        return restTemplate.getForObject(baseUrl1 + "/api/holding/" + bookrecno, JSONObject.class);
    }

    @Override
    public JSONObject book(String bookrecno) {
        return restTemplate.getForObject(baseUrl1 + "/api/book/" + bookrecno, JSONObject.class);
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
