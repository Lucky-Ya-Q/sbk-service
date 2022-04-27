package com.ruoyi.service.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.domain.SbkUser;
import com.ruoyi.service.dto.FwmmxgParam;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.dto.RyjcxxbgParam;
import com.ruoyi.service.util.SbkParamUtils;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "社保卡基础功能")
@RestController
@RequestMapping("/api/sbk/base")
public class SbkBaseController extends SbkCommonController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 人员基础信息变更
     */
    @ApiOperation("人员基础信息变更")
    @PostMapping("/ryjcxxbg")
    public AjaxResult ryjcxxbg(@RequestBody @Validated RyjcxxbgParam ryjcxxbgParam) {
        SbkUser sbkUser = getSbkUser();
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
        SbkUser sbkUser = getSbkUser();
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
        SbkUser sbkUser = getSbkUser();
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
        SbkUser sbkUser = getSbkUser();
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
        SbkUser sbkUser = getSbkUser();
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
        SbkUser sbkUser = getSbkUser();
        // 服务密码修改
        String keyInfo = sbkUser.getAac002() + "|" + sbkUser.getAac003() + "|" + sbkUser.getAaz500() + "|" + fwmmxgParam.getOldPassword() + "|" + fwmmxgParam.getNewPassword();
        Result result = sbkService.getResult("0821020", keyInfo);
        return toAjax(result);
    }

    /**
     * 公积金查询
     */
    @ApiOperation("公积金查询")
    @GetMapping("/gjjcx")
    public AjaxResult gjjcx() {
        SbkUser sbkUser = getSbkUser();
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
}
