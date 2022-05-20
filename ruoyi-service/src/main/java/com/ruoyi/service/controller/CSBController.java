package com.ruoyi.service.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.domain.SbkUser;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.service.CSBService;
import com.ruoyi.service.service.SbkService;
import com.ruoyi.service.util.AESUtils;
import com.ruoyi.service.util.SbkParamUtils;
import com.ruoyi.service.util.SbkUserUtils;
import com.tecsun.sm.utils.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "CBS相关接口")
@RestController
@RequestMapping("/api/cbs")
public class CSBController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CSBService csbService;
    @Autowired
    private SbkService sbkService;

    /**
     * 电子社保卡认证接口
     */
    @ApiOperation("电子社保卡认证接口")
    @GetMapping("/auth_encrypt")
    public AjaxResult auth_encrypt() throws IOException {
        SbkUser sbkUser = SbkUserUtils.getSbkUser(request);
        // 社保卡基本信息查询
        String keyInfo = sbkUser.getAac002() + "|" + sbkUser.getAac003() + "|" + sbkUser.getAaz500();
        Result jbxxcxResult = sbkService.getResult("0811014", keyInfo);
        if (!"200".equals(jbxxcxResult.getStatusCode())) {
            return AjaxResult.error(jbxxcxResult.getMessage());
        }
        Map<String, String> data = (Map<String, String>) jbxxcxResult.getData();
        String jbxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
        String[] jbxxcxArr = jbxxcx.split("\\|");

        Map<String, Object> result = new HashMap<>();
        result.put("aac002", jbxxcxArr[1]);
        result.put("aac003", jbxxcxArr[0]);
        result.put("phone", jbxxcxArr[12]);
        String encrypt = AESUtils.encrypt(JSON.toJSONString(result), AESUtils.KEY);
        return AjaxResult.success(encrypt);
    }

    /**
     * 二维码-验码接口
     */
    @ApiOperation("二维码-验码接口")
    @Log(title = "二维码-验码接口", businessType = BusinessType.OTHER)
    @GetMapping("/qrcode_valid_encrypt")
    public AjaxResult qrcode_valid_encrypt(String qrCode) throws IOException {
        JSONObject jsonObject = csbService.qrcode_valid_encrypt(qrCode, "01", "系统名称");
        // 社保卡基本信息查询
        String keyInfo = jsonObject.getString("aac002") + "|" + jsonObject.getString("aac003") + "|" + jsonObject.getString("aaz500");
        Result jbxxcxResult = sbkService.getResult("0811014", keyInfo);
        if (!"200".equals(jbxxcxResult.getStatusCode())) {
            return AjaxResult.error(jbxxcxResult.getMessage());
        }
        Map<String, String> data = (Map<String, String>) jbxxcxResult.getData();
        String jbxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
        String[] jbxxcxArr = jbxxcx.split("\\|");
        Map<String, Object> result = new HashMap<>();
        result.put("aac002", "aac002");
        result.put("aac003", jbxxcxArr[0]);
        result.put("phone", jbxxcxArr[12]);
        String encrypt = AESUtils.encrypt(JSON.toJSONString(result), AESUtils.KEY);
        return AjaxResult.success(encrypt);
    }
}
