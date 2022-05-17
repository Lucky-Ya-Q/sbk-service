package com.ruoyi.service.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.service.CSBService;
import com.ruoyi.service.util.SbkUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "CBS相关接口")
@RestController
@RequestMapping("/api/cbs")
public class CSBController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CSBService csbService;

    /**
     * 电子社保卡认证接口
     */
    @ApiOperation("电子社保卡认证接口")
    @Log(title = "电子社保卡认证接口", businessType = BusinessType.OTHER)
    @GetMapping("/auth_encrypt")
    public AjaxResult auth_encrypt() {
        return AjaxResult.success(SbkUserUtils.getSbkUser(request));
    }

    /**
     * 二维码-验码接口
     */
    @ApiOperation("二维码-验码接口")
    @Log(title = "二维码-验码接口", businessType = BusinessType.OTHER)
    @GetMapping("/qrcode_valid_encrypt")
    public AjaxResult qrcode_valid_encrypt(String qrCode) {
        return AjaxResult.success(csbService.qrcode_valid_encrypt(qrCode, "01", "系统名称"));
    }
}
