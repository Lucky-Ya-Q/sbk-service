package com.ruoyi.service.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.service.CSBService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "单元测试")
@RestController
@RequestMapping("/api/test")
public class SbkTestController {
    @Autowired
    private CSBService csbService;

    /**
     * 二维码-验码接口
     */
    @ApiOperation("二维码-验码接口")
    @GetMapping("/qrcode_valid_encrypt")
    public AjaxResult qrcode_valid_encrypt(String qrCode) {
        return AjaxResult.success(csbService.qrcode_valid_encrypt(qrCode, "01", "系统名称"));
    }
}
