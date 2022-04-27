package com.ruoyi.service.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.domain.SbkUser;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.service.CSBService;
import com.ruoyi.service.service.SbkService;
import com.ruoyi.service.util.AESUtils;
import com.ruoyi.service.util.SbkParamUtils;
import com.tecsun.sm.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class SbkCommonController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CSBService csbService;
    @Autowired
    protected SbkService sbkService;

    protected SbkUser getSbkUser() {
        String security = request.getParameter("security");
        if (StrUtil.isEmpty(security)) {
            throw new ServiceException("security参数不能为空");
        }
        String signNo = null;
        try {
            signNo = JSON.parseObject(AESUtils.decrypt(security, "6vffkptbol2tf7bk")).getString("signNo");
        } catch (Exception e) {
            throw new ServiceException("获取签发号错误");
        }
        String esscNo = csbService.auth_encrypt(signNo).getString("esscNo");
        // 电子社保卡基本信息
        Result result = sbkService.getResult("0811015", esscNo);
        if (!"200".equals(result.getStatusCode())) {
            throw new ServiceException(result.getMessage());
        }
        Map<String, String> data = (Map<String, String>) result.getData();
        String dzsbkjbxx = "发卡地行政区划代码|AD0351899|卡识别码|130125200002094513|刘元博|社保卡状态";
        try {
            dzsbkjbxx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }

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

    protected AjaxResult toAjax(Result result) {
        if ("200".equals(result.getStatusCode())) {
            return AjaxResult.success(result.getMessage());
        } else {
            return AjaxResult.error(result.getMessage());
        }
    }

    protected AjaxResult toAjax(Result result, String success) {
        if ("200".equals(result.getStatusCode())) {
            return AjaxResult.success(success);
        } else {
            return AjaxResult.error(result.getMessage());
        }
    }
}
