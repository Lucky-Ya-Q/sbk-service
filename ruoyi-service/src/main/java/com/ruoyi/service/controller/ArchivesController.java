package com.ruoyi.service.controller;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.domain.WxArchives;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.dto.XbkzgjyParam;
import com.ruoyi.service.service.SbkService;
import com.ruoyi.service.service.WxArchivesService;
import com.ruoyi.service.util.SbkParamUtils;
import com.tecsun.sm.utils.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "社保卡申领")
@RestController
@RequestMapping("/Archivesssapi")
public class ArchivesController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private SbkService sbkService;
    @Autowired
    private WxArchivesService wxArchivesService;

    /**
     * 获取微信用户信息
     */
    @Log(title = "社保卡申领", businessType = BusinessType.OTHER)
//    @ApiOperation("获取微信用户信息")
    @PostMapping("/get_userinfo")
    public AjaxResult get_userinfo(@RequestBody Map<String, String> map) throws WxErrorException {
        String wx_code = map.get("wx_code");
        WxOAuth2AccessToken wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(wx_code);
        WxOAuth2UserInfo wxOAuth2UserInfo = wxMpService.getOAuth2Service().getUserInfo(wxOAuth2AccessToken, null);
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxOAuth2UserInfo.getOpenid());
        return AjaxResult.success(wxMpUser);
    }

    /**
     * 判断是否已办卡或已申领
     */
    @Log(title = "社保卡申领", businessType = BusinessType.OTHER)
    @ApiOperation("判断是否已办卡或已申领")
    @PostMapping("/get_makecard_status")
    public AjaxResult get_makecard_status(@RequestBody @Validated XbkzgjyParam xbkzgjyParam) throws IOException {
        List<String> whiteList = new ArrayList<>();
        whiteList.add("130125200002094513"); // 刘元博
        whiteList.add("130133200001022737"); // 张梦泽
        if (!whiteList.contains(xbkzgjyParam.getSfzh())) {
            // 办卡资格校验
            String keyInfo = xbkzgjyParam.getSfzh() + "|" + xbkzgjyParam.getXm() + "|1";
            Result result = sbkService.getResult("0811011", keyInfo);
            if (!"200".equals(result.getStatusCode())) {
                return AjaxResult.error(result.getMessage());
            }
            Map<String, String> data = (Map<String, String>) result.getData();
            String bkzgjy = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
            String[] bkzgjyArr = bkzgjy.split("\\|");
            if ("0".equals(bkzgjyArr[0])) {
                return AjaxResult.error("您已有社保卡采集信息");
            }
        }

        String code = request.getParameter("code");
        String source = "0";
        if ("d564edee1cb541b2b1925dcaeb54ed0c".equals(code)) {
            source = "7";
        }

        WxArchives wxArchives = wxArchivesService.selectOneByLambdaQueryWrapper(new LambdaQueryWrapper<WxArchives>().eq(WxArchives::getCardNum, xbkzgjyParam.getSfzh()).eq(WxArchives::getName, xbkzgjyParam.getXm()));
        if (wxArchives != null) {
            // 审核状态：0代表未审核 1代表审核通过 2代表审核未通过
            String examineStatus = wxArchives.getExamineStatus();
            if ("0".equals(examineStatus)) {
                if ("9".equals(wxArchives.getStepStatus())) {
                    return AjaxResult.error("采集信息审核中");
                } else {
                    if (source.equals(wxArchives.getSource())) {
                        return new AjaxResult(201, "请继续填写采集信息");
                    } else {
                        return AjaxResult.error("请继续在首次申领渠道修改信息");
                    }
                }
            } else if ("1".equals(examineStatus)) {
                return AjaxResult.error("采集信息审核已通过");
            } else if ("2".equals(examineStatus)) {
                if (source.equals(wxArchives.getSource())) {
                    return new AjaxResult(201, "采集信息审核未通过");
                } else {
                    return AjaxResult.error("请继续在首次申领渠道修改信息");
                }
            }
        }
        return AjaxResult.success("您可以新采集信息");
    }

    /**
     * 获取录入信息
     */
    @Log(title = "社保卡申领", businessType = BusinessType.OTHER)
    @ApiOperation("获取录入信息")
    @PostMapping("/get_inputinfo")
    public AjaxResult get_inputinfo(@RequestBody @Validated XbkzgjyParam xbkzgjyParam) {
        if (!IdcardUtil.isValidCard(xbkzgjyParam.getSfzh())) {
            return AjaxResult.error("身份证号码格式错误");
        }
        int age = IdcardUtil.getAgeByIdCard(xbkzgjyParam.getSfzh());
        LambdaQueryWrapper<WxArchives> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxArchives::getCardNum, xbkzgjyParam.getSfzh());
        queryWrapper.eq(WxArchives::getName, xbkzgjyParam.getXm());
        WxArchives wxArchives = wxArchivesService.selectOneByLambdaQueryWrapper(queryWrapper);
        if (wxArchives != null) {
            // TODO: 2022/7/20 0020 设置电子照片
        } else {
            wxArchives = new WxArchives();
            wxArchives.setCardNum(xbkzgjyParam.getSfzh());
        }
        return AjaxResult.success(wxArchives);
    }
}
