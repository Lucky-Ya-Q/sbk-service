package com.ruoyi.service.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.domain.WxSmspersonEms;
import com.ruoyi.service.mapper.WxSmspersonEmsMapper;
import com.ruoyi.service.service.WxSmspersonEmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxSmspersonEmsServiceImpl extends ServiceImpl<WxSmspersonEmsMapper, WxSmspersonEms> implements WxSmspersonEmsService {
    @Autowired
    private WxSmspersonEmsMapper wxSmspersonEmsMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public JSONObject selectMailInfoBySfzh(String sfzh) {
        WxSmspersonEms wxSmspersonEms = wxSmspersonEmsMapper.selectOne(new LambdaQueryWrapper<WxSmspersonEms>().like(WxSmspersonEms::getIdcard, sfzh).last("limit 1"));
        if (wxSmspersonEms == null) {
            throw new ServiceException("没有查到物流单号");
        }
        AES aes = SecureUtil.aes("94DA411B9C39B410".getBytes());
        String url = "http://ipps.hbwkd.cn/ipps/orderPay/api/EmsTrail/EmsTrailAction.do?actionType=getMailInfo";
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("AUTH_CODE", "94D5C8EAA808A9A5E050007F01005B3C");
        hashMap.put("CUST_APPID", "wxde85bc4bf1f7629a");
        hashMap.put("MAIL_NUM", wxSmspersonEms.getMailnum());
        String content = aes.encryptBase64(JSON.toJSONString(hashMap));
        String result = restTemplate.postForObject(url, content, String.class);
        if (result == null) {
            throw new ServiceException("没有查到物流信息");
        }
        result = aes.decryptStr(result.replaceAll("\\r\\n", ""));
        return JSON.parseObject(result);
    }
}
