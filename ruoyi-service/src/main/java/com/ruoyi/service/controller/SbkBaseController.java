package com.ruoyi.service.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.service.domain.SbkUser;
import com.ruoyi.service.dto.FwmmxgParam;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.dto.RyjcxxbgParam;
import com.ruoyi.service.dto.ZkjdcxParam;
import com.ruoyi.service.service.SbkBaseService;
import com.ruoyi.service.service.SbkService;
import com.ruoyi.service.service.WxSmspersonEmsService;
import com.ruoyi.service.util.HttpUtils;
import com.ruoyi.service.util.SbkParamUtils;
import com.ruoyi.service.util.SbkUserUtils;
import com.tecsun.sm.utils.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "社保卡基础功能")
@RestController
@RequestMapping("/api/sbk/base")
public class SbkBaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SbkService sbkService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SbkBaseService sbkBaseService;
    @Autowired
    private WxSmspersonEmsService wxSmspersonEmsService;


    /**
     * 人员基础信息变更
     */
    @ApiOperation("人员基础信息变更")
    @PostMapping("/ryjcxxbg")
    public AjaxResult ryjcxxbg(@RequestBody @Validated RyjcxxbgParam ryjcxxbgParam) {
        SbkUser sbkUser = SbkUserUtils.getSbkUser(request);
        // 人员基础信息变更
        String keyInfo = sbkUser.getAac002() + "|" + sbkUser.getAac003() + "|" + ryjcxxbgParam.getJzdz() + "|" + ryjcxxbgParam.getYddh() + "|" + ryjcxxbgParam.getQsrq() + "|" + ryjcxxbgParam.getZzrq() + "|" + ryjcxxbgParam.getZy();
        Result result = sbkService.getResult("0821014", keyInfo);
        return toAjax(result);
    }

    /**
     * 基本信息查询
     */
    @ApiOperation("基本信息查询")
    @GetMapping("/jbxxcx")
    public AjaxResult jbxxcx() throws IOException {
        SbkUser sbkUser = SbkUserUtils.getSbkUser(request);
        // 社保卡基本信息查询
        String keyInfo = sbkUser.getAac002() + "|" + sbkUser.getAac003() + "|" + sbkUser.getAaz500();
        Result result = sbkService.getResult("0811014", keyInfo);
        if (!"200".equals(result.getStatusCode())) {
            return AjaxResult.error(result.getMessage());
        }
        Map<String, String> data = (Map<String, String>) result.getData();
        String jbxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
        String[] jbxxcxArr = jbxxcx.split("\\|");
        return AjaxResult.success(jbxxcxArr);
    }

    /**
     * 解挂
     */
    @ApiOperation("解挂")
    @PostMapping("/jg")
    public AjaxResult jg() {
        SbkUser sbkUser = SbkUserUtils.getSbkUser(request);
        // 解挂
        String keyInfo = sbkUser.getAac002() + "|" + sbkUser.getAac003() + "|" + sbkUser.getAaz500();
        Result result = sbkService.getResult("0821015", keyInfo);
        return toAjax(result);
    }

    /**
     * 正式挂失
     */
    @ApiOperation("正式挂失")
    @PostMapping("/zsgs")
    public AjaxResult zsgs() {
        SbkUser sbkUser = SbkUserUtils.getSbkUser(request);
        // 正式挂失
        String keyInfo = sbkUser.getAac002() + "|" + sbkUser.getAac003() + "|" + sbkUser.getAaz500();
        Result result = sbkService.getResult("0821017", keyInfo);
        return toAjax(result);
    }

    /**
     * 服务密码重置
     */
    @ApiOperation("服务密码重置")
    @PostMapping("/fwmmcz")
    public AjaxResult fwmmcz() {
        SbkUser sbkUser = SbkUserUtils.getSbkUser(request);
        String password = "123456";
        // 服务密码重置
        String keyInfo = sbkUser.getAac002() + "|" + sbkUser.getAac003() + "|" + sbkUser.getAaz500() + "|" + password + "|";
        Result result = sbkService.getResult("0821019", keyInfo);
        return toAjax(result, "密码重置为" + password);
    }

    /**
     * 服务密码修改
     */
    @ApiOperation("服务密码修改")
    @PostMapping("/fwmmxg")
    public AjaxResult fwmmxg(@RequestBody @Validated FwmmxgParam fwmmxgParam) {
        SbkUser sbkUser = SbkUserUtils.getSbkUser(request);
        // 服务密码修改
        String keyInfo = sbkUser.getAac002() + "|" + sbkUser.getAac003() + "|" + sbkUser.getAaz500() + "|" + fwmmxgParam.getOldPassword() + "|" + fwmmxgParam.getNewPassword();
        Result result = sbkService.getResult("0821020", keyInfo);
        return toAjax(result);
    }

    /**
     * 制卡进度查询
     */
    @ApiOperation("制卡进度查询")
    @GetMapping("/zkjdcx")
    public AjaxResult zkjdcx(@Validated ZkjdcxParam zkjdcxParam) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        // 申领信息查询
        Result result = sbkService.getResult("0811012", zkjdcxParam.getSfzh() + "|" + zkjdcxParam.getXm());
        if (!"200".equals(result.getStatusCode())) {
            return AjaxResult.error(result.getMessage());
        }
        Map<String, String> data = (Map<String, String>) result.getData();
        String slxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
        String[] slxxcxArr = slxxcx.split("\\|");

        resultMap.put("shenling", sbkBaseService.getShenLingData(zkjdcxParam, slxxcxArr[12]));
        resultMap.put("buhuanka", sbkBaseService.getBuHuanKaData(zkjdcxParam, slxxcxArr[12]));
        return AjaxResult.success(resultMap);
    }

    /**
     * 物流信息查询
     */
    @ApiOperation("物流信息查询")
    @GetMapping("/wlxxcx")
    public AjaxResult wlxxcx(String sfzh, String wldh) {
        if (!IdcardUtil.isValidCard(sfzh)) {
            return AjaxResult.error("身份证号码格式错误");
        }
        if (StrUtil.isNotEmpty(wldh)) {
            return AjaxResult.success(wxSmspersonEmsService.selectMailInfoByWldh(wldh));
        } else {
            return AjaxResult.success(wxSmspersonEmsService.selectMailInfoBySfzh(sfzh));
        }
    }

    /**
     * 公积金查询
     */
    @ApiOperation("公积金查询")
    @GetMapping("/gjjcx")
    public AjaxResult gjjcx() {
        SbkUser sbkUser = SbkUserUtils.getSbkUser(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://10.36.1.244:13080/share/C13010/hf/grjbxxcx.service";
        httpHeaders.set("sendnode", "C13010|ls");
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("zjbzxbm", "C13010");
        hashMap.put("share", "share");
        hashMap.put("name", sbkUser.getAac003());
        hashMap.put("zjhm", sbkUser.getAac002());

        HttpEntity<Object> httpEntity = new HttpEntity<>(hashMap, httpHeaders);
        return AjaxResult.success(restTemplate.postForObject(url, httpEntity, String.class));
    }

    /**
     * 社保卡识别
     */
    @ApiOperation("社保卡识别")
    @PostMapping("/social_security_card")
    public AjaxResult social_security_card(MultipartFile file) {
        try {
            String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/social_security_card";
            String imgStr = Base64.encode(file.getBytes());
            String imgParam = URLEncoder.encode(imgStr, "utf-8");

            String param = "image=" + imgParam;
            String result = HttpUtils.post(url, getAccessToken(), param);

            JSONObject jsonObject = JSON.parseObject(result);
            String errorMsg = jsonObject.getString("error_msg");
            if (StrUtil.isNotEmpty(errorMsg)) {
                return AjaxResult.error(errorMsg);
            }
            Object wordsResult = jsonObject.get("words_result");
            return AjaxResult.success(wordsResult);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    private String getAccessToken() {
        String accessToken = redisCache.getCacheObject("accessToken");
        if (StrUtil.isNotEmpty(accessToken)) {
            return accessToken;
        }
        String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=KfkmgdfWa9mKugYz79ois9RT&client_secret=DWMstB19hPgb7THuxrBtZGCrES9dlqUA";
        String result = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSON.parseObject(result);
        accessToken = jsonObject.getString("access_token");
        redisCache.setCacheObject("accessToken", accessToken, 10, TimeUnit.DAYS);
        return accessToken;
    }

    private AjaxResult toAjax(Result result) {
        if ("200".equals(result.getStatusCode())) {
            return AjaxResult.success(result.getMessage());
        } else {
            return AjaxResult.error(result.getMessage());
        }
    }

    private AjaxResult toAjax(Result result, String success) {
        if ("200".equals(result.getStatusCode())) {
            return AjaxResult.success(success);
        } else {
            return AjaxResult.error(result.getMessage());
        }
    }
}
