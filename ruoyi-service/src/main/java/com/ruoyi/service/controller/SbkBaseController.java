package com.ruoyi.service.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.domain.SbkUser;
import com.ruoyi.service.dto.FwmmxgParam;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.dto.RyjcxxbgParam;
import com.ruoyi.service.service.CSBService;
import com.ruoyi.service.service.SbkService;
import com.ruoyi.service.util.AESUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "社保卡基础功能")
@RestController
@RequestMapping("/api/sbk/base")
public class SbkBaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CSBService csbService;
    @Autowired
    private SbkService sbkService;
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
    public Object gjjcx() {
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
        return restTemplate.postForObject(url, httpEntity, String.class);
    }

    private SbkUser getSbkUser() {
        String security = request.getParameter("security");
        String signNo = JSON.parseObject(AESUtils.decrypt(security, "6vffkptbol2tf7bk")).getString("signNo");
        String esscNo = csbService.auth_encrypt(signNo).getString("esscNo");
        // 电子社保卡基本信息
        Result result = sbkService.getResult("0811015", esscNo);
        if (!"200".equals(result.getStatusCode())) {
            throw new ServiceException(result.getMessage());
        }
        Map<String, String> data = (Map<String, String>) result.getData();
        String dzsbkjbxx = null;
        try {
            dzsbkjbxx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }

        // String dzsbkjbxx = "发卡地行政区划代码|AD0351899|卡识别码|130125200002094513|刘元博|社保卡状态";
        String[] dzsbkjbxxArr = dzsbkjbxx.split("\\|");

        SbkUser sbkUser = new SbkUser();
        sbkUser.setAab301(dzsbkjbxxArr[0]);
        sbkUser.setAaz500(dzsbkjbxxArr[1]);
        sbkUser.setAaz501(dzsbkjbxxArr[2]);
        sbkUser.setAac002(dzsbkjbxxArr[3]);
        sbkUser.setAac003(dzsbkjbxxArr[4]);
        sbkUser.setAaz502(dzsbkjbxxArr[5]);
        return sbkUser;
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
