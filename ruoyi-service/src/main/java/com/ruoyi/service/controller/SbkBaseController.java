package com.ruoyi.service.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.domain.*;
import com.ruoyi.service.dto.FwmmxgParam;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.dto.RyjcxxbgParam;
import com.ruoyi.service.dto.ZkjdcxParam;
import com.ruoyi.service.service.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private WxArchivesService wxArchivesService;
    @Autowired
    private WxArchivesStatusService wxArchivesStatusService;
    @Autowired
    private WxBukaInfoService wxBukaInfoService;
    //    @Autowired
//    private SbkBaseService sbkBaseService;
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
            resultMap.put("shenling", getShenLingData(zkjdcxParam, "100"));
            resultMap.put("buhuanka", new ArrayList<>());
        } else {
            Map<String, String> data = (Map<String, String>) result.getData();
            String slxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
            String[] slxxcxArr = slxxcx.split("\\|");

            resultMap.put("shenling", getShenLingData(zkjdcxParam, slxxcxArr[12]));
            resultMap.put("buhuanka", getBuHuanKaData(zkjdcxParam, slxxcxArr[12]));
        }

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

    private Map<String, Object> getShenLingData(ZkjdcxParam zkjdcxParam, String state) {
        // shenling
        Map<String, Object> shenlingMap = new HashMap<>();
        // shenling.data 所有数据
        List<Map<String, Object>> resultList = new ArrayList<>();
        // shenling.data 德生数据
        List<Map<String, Object>> mapList = new ArrayList<>();

        String newCardCode1 = "1、制卡信息采集已审核通过（审核通过后五个工作日完成制卡，请耐心等待）。";
        String newCardCode2 = "2、正在写入社保信息，请耐心等待";
        String newCardCode3 = "3、制卡成功，待邮寄";
        String newCardCode4 = "4、已邮寄";
        switch (state) {
            case "32": {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("flag", 1);
                map1.put("info", newCardCode1);
                Map<String, Object> map2 = new HashMap<>();
                map2.put("flag", 1);
                map2.put("info", newCardCode2);
                Map<String, Object> map3 = new HashMap<>();
                map3.put("flag", 1);
                map3.put("info", newCardCode3);
                Map<String, Object> map4 = new HashMap<>();
                map4.put("flag", 0);
                map4.put("info", newCardCode4);
//                Map<String, Object> map5 = new HashMap<>();
//                map5.put("flag", 1);
//                map5.put("info", "5、个人已领取社保卡");

                map1.put("time_flag", 0);
                map2.put("time_flag", 0);
                map3.put("time_flag", 0);
                map4.put("time_flag", 0);
//                map5.put("time_flag", 0);
                mapList.add(map1);
                mapList.add(map2);
                mapList.add(map3);
                mapList.add(map4);
//                mapList.add(map5);
                break;
            }
            case "33": {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("flag", 1);
                map1.put("info", newCardCode1);
                Map<String, Object> map2 = new HashMap<>();
                map2.put("flag", 1);
                map2.put("info", newCardCode2);
                Map<String, Object> map3 = new HashMap<>();
                map3.put("flag", 1);
                map3.put("info", newCardCode3);
                Map<String, Object> map4 = new HashMap<>();
                map4.put("flag", 0);
                map4.put("info", newCardCode4);
//                Map<String, Object> map5 = new HashMap<>();
//                map5.put("flag", 1);
//                map5.put("info", "5、单位已领取社保卡");

                map1.put("time_flag", 0);
                map2.put("time_flag", 0);
                map3.put("time_flag", 0);
                map4.put("time_flag", 0);
//                map5.put("time_flag", 0);
                mapList.add(map1);
                mapList.add(map2);
                mapList.add(map3);
                mapList.add(map4);
//                mapList.add(map5);
                break;
            }
            case "11": {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("flag", 1);
                map1.put("info", newCardCode1);
                Map<String, Object> map2 = new HashMap<>();
                map2.put("flag", 0);
                map2.put("info", newCardCode2);
                Map<String, Object> map3 = new HashMap<>();
                map3.put("flag", 0);
                map3.put("info", newCardCode3);
                Map<String, Object> map4 = new HashMap<>();
                map4.put("flag", 0);
                map4.put("info", newCardCode4);
//                Map<String, Object> map5 = new HashMap<>();
//                map5.put("flag", 0);
//                map5.put("info", "5、单位或者个人已领取社保卡");

                map1.put("time_flag", 0);
                map2.put("time_flag", 0);
                map3.put("time_flag", 0);
                map4.put("time_flag", 0);
//                map5.put("time_flag", 0);
                mapList.add(map1);
                mapList.add(map2);
                mapList.add(map3);
                mapList.add(map4);
//                mapList.add(map5);
                break;
            }
            default: {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("flag", 0);
                map1.put("info", newCardCode1);
                Map<String, Object> map2 = new HashMap<>();
                map2.put("flag", 0);
                map2.put("info", newCardCode2);
                Map<String, Object> map3 = new HashMap<>();
                map3.put("flag", 0);
                map3.put("info", newCardCode3);
                Map<String, Object> map4 = new HashMap<>();
                map4.put("flag", 0);
                map4.put("info", newCardCode4);
//                Map<String, Object> map5 = new HashMap<>();
//                map5.put("flag", 0);
//                map5.put("info", "5、单位或者个人已领取社保卡");

                map1.put("time_flag", 0);
                map2.put("time_flag", 0);
                map3.put("time_flag", 0);
                map4.put("time_flag", 0);
//                map5.put("time_flag", 0);
                mapList.add(map1);
                mapList.add(map2);
                mapList.add(map3);
                mapList.add(map4);
//                mapList.add(map5);
                break;
            }
        }

        WxArchives wxArchives = wxArchivesService.selectOneByLambdaQueryWrapper(new LambdaQueryWrapper<WxArchives>().eq(WxArchives::getCardNum, zkjdcxParam.getSfzh()).eq(WxArchives::getStepStatus, 9));
        if (wxArchives != null) {
            if (wxArchives.getIsMail().equals("1")) {
//                mapList.get(4).put("info", "5、个人邮寄领取社保卡");
            }
        }

        WxArchivesStatus wxArchivesStatus = wxArchivesStatusService.selectOneByLambdaQueryWrapper(new LambdaQueryWrapper<WxArchivesStatus>().eq(WxArchivesStatus::getCardNum, zkjdcxParam.getSfzh()).eq(WxArchivesStatus::getStepStatus, 9).in(WxArchivesStatus::getExamineStatus, "0", "1", "2"));
        if (wxArchivesStatus != null) {
            wxArchives = wxArchivesService.selectOneByLambdaQueryWrapper(new LambdaQueryWrapper<WxArchives>().eq(WxArchives::getCardNum, zkjdcxParam.getSfzh()));

            shenlingMap.put("shijian", wxArchives.getAddTime());
            switch (wxArchives.getSource()) {
                case "1":
                    shenlingMap.put("qudao", "微信公众号");
                    break;
                case "3":
                    shenlingMap.put("qudao", "支付宝生活号");
                    break;
                case "5":
                    shenlingMap.put("qudao", "电子社保卡");
                    break;
                case "6":
                    shenlingMap.put("qudao", "赞皇社保卡");
                    break;
                case "7":
                    shenlingMap.put("qudao", "鹿泉社保卡");
                    break;
                case "8":
                    shenlingMap.put("qudao", "灵寿社保卡");
                    break;
            }

            switch (wxArchivesStatus.getIsMail()) {
                case "1":
                    wxArchivesStatus.setIsMail("邮寄到家");
                    break;
                case "0":
                    wxArchivesStatus.setIsMail("网点领取“" + wxArchives.getLingkaNet() + "”");
                    mapList.remove(3);
                    mapList.get(0).put("info", "1、制卡信息采集已审核通过（请到指定网点领取社保卡）。");
                    mapList.get(2).put("info", "3、制卡成功，已领取社保卡");
                    break;
                case "2":
                    wxArchivesStatus.setIsMail("银行网点领取“" + wxArchives.getLingkaNet() + "”");
                    mapList.remove(3);
                    mapList.get(0).put("info", "1、制卡信息采集已审核通过（请到指定网点领取社保卡）。");
                    mapList.get(2).put("info", "3、制卡成功，已领取社保卡");
                    break;
            }
            if (wxArchivesStatus.getIsMail().equals("邮寄到家")) {
                LambdaQueryWrapper<WxSmspersonEms> queryWrapper = new LambdaQueryWrapper<WxSmspersonEms>()
                        .like(WxSmspersonEms::getIdcard, wxArchives.getCardNum())
                        .ge(WxSmspersonEms::getImporttime, wxArchivesStatus.getExamineTime());
                WxSmspersonEms wxSmspersonEms = wxSmspersonEmsService.selectOneByLambdaQueryWrapper(queryWrapper);
                if (wxSmspersonEms == null) {
                    mapList.get(3).put("flag", 0);
                } else {
                    mapList.get(3).put("flag", 1);
                }
            }


            Map<String, Object> mapa = new HashMap<>();
            mapa.put("flag", 1);
            mapa.put("add_time", wxArchivesStatus.getAddTime());
            mapa.put("nickname", wxArchives.getName());
            mapa.put("is_mail", wxArchivesStatus.getIsMail());

            Map<String, Object> mapb = new HashMap<>();
            switch (wxArchivesStatus.getExamineStatus()) {
                case "0":
                    mapb.put("flag", 0);
                    mapb.put("status", 0);
                    mapb.put("msg", "未初审");
                    break;
                case "1":
                    mapb.put("flag", 1);
                    mapb.put("status", 1);
                    mapb.put("examine_time", wxArchivesStatus.getExamineTime());
                    mapb.put("msg", "初审通过");
                    break;
                case "2":
                    mapb.put("flag", 2);
                    mapb.put("status", 1);
                    mapb.put("examine_time", wxArchivesStatus.getExamineTime());
                    mapb.put("msg", "初审驳回。驳回原因：" + wxArchivesStatus.getReason() + "。");
                    break;
            }

            Map<String, Object> mapc = new HashMap<>();
            if (wxArchivesStatus.getIsJpg().equals("2")) {
                mapc.put("flag", 1);
                mapc.put("msg", "已导出");
                mapc.put("daochu_time", wxArchivesStatus.getJpgAddTime());
            } else {
                if (wxArchivesStatus.getIsMail().equals("网点领取“" + wxArchives.getLingkaNet() + "”") && wxArchivesStatus.getExamineStatus().equals("1")) {
                    mapc.put("flag", 1);
                    mapc.put("msg", "已导出");
                    mapc.put("daochu_time", wxArchivesStatus.getJpgAddTime());
                } else {
                    mapc.put("flag", 0);
                    mapc.put("msg", "未导出");
                }
            }

            String source = "【石家庄】";
            switch (wxArchivesStatus.getSource()) {
                case "6":
                    source = "【赞皇】";
                    break;
                case "7":
                    source = "【鹿泉】";
                    break;
                case "8":
                    source = "【灵寿】";
                    break;
            }
            mapa.put("info", "A、" + source + "首次制卡信息提交成功");
            mapb.put("info", "B、" + source + "制卡信息网上初审");
            mapc.put("info", "C、" + source + "制卡信息导出到省级制卡平台");

            resultList.add(mapa);
            resultList.add(mapb);
            resultList.add(mapc);


        } else {
            mapList.remove(3);
        }
        resultList.addAll(mapList);

        shenlingMap.put("data", resultList);
        return shenlingMap;
    }

    private List<Map<String, Object>> getBuHuanKaData(ZkjdcxParam zkjdcxParam, String state) throws IOException {
        List<Map<String, Object>> buhuankaList = new ArrayList<>();

        List<WxBukaInfo> wxBukaInfoList = wxBukaInfoService.selectListByLambdaQueryWrapper(new LambdaQueryWrapper<WxBukaInfo>().eq(WxBukaInfo::getIdcardno, zkjdcxParam.getSfzh()).eq(WxBukaInfo::getStepStatus, 9).in(WxBukaInfo::getExamineStatus, "0", "1", "2").orderByDesc(WxBukaInfo::getId));
        for (WxBukaInfo wxBukaInfo : wxBukaInfoList) {
            // buhuanka
            Map<String, Object> buhuankaMap = new HashMap<>();
            buhuankaMap.put("shijian", wxBukaInfo.getAddTime());
            switch (String.valueOf(wxBukaInfo.getWebsource())) {
                case "1":
                    buhuankaMap.put("qudao", "微信公众号");
                    break;
                case "3":
                    buhuankaMap.put("qudao", "支付宝生活号");
                    break;
                case "5":
                    buhuankaMap.put("qudao", "电子社保卡");
                    break;
                case "6":
                    buhuankaMap.put("qudao", "赞皇社保卡");
                    break;
                case "7":
                    buhuankaMap.put("qudao", "鹿泉社保卡");
                    break;
                case "8":
                    buhuankaMap.put("qudao", "灵寿社保卡");
                    break;
            }
            // shenling.data 所有数据
            List<Map<String, Object>> resultList = new ArrayList<>();
            // shenling.data 德生数据
            List<Map<String, Object>> mapList = new ArrayList<>();

            Map<String, Object> mapa = new HashMap<>();
            mapa.put("flag", 1);
            mapa.put("add_time", wxBukaInfo.getAddTime());
            mapa.put("nickname", wxBukaInfo.getKaName());
            mapa.put("is_mail", "邮寄到家");

            Map<String, Object> mapb = new HashMap<>();
            switch (wxBukaInfo.getExamineStatus()) {
                case 0:
                    mapb.put("flag", 0);
                    mapb.put("status", 0);
                    mapb.put("msg", "未初审");
                    break;
                case 1:
                    mapb.put("flag", 1);
                    mapb.put("status", 1);
                    mapb.put("examine_time", wxBukaInfo.getExamineTime());
                    mapb.put("msg", "初审通过");
                    break;
                case 2:
                    mapb.put("flag", 2);
                    mapb.put("status", 1);
                    mapb.put("examine_time", wxBukaInfo.getExamineTime());
                    mapb.put("msg", "初审驳回。驳回原因：" + wxBukaInfo.getRejectReason() + "。");
                    break;
            }

            Map<String, Object> mapc = new HashMap<>();
            if (wxBukaInfo.getIsJpg() == 2) {
                mapc.put("flag", 1);
                mapc.put("msg", "已导出");
                mapc.put("daochu_time", wxBukaInfo.getJpgAddTime());
            } else {
                if (wxBukaInfo.getExamineStatus() == 1) {
                    mapc.put("flag", 1);
                    mapc.put("msg", "已导出");
                    mapc.put("daochu_time", wxBukaInfo.getJpgAddTime());
                } else {
                    mapc.put("flag", 0);
                    mapc.put("msg", "未导出");
                }
            }

            String source = "【石家庄】";
            switch (wxBukaInfo.getSource()) {
                case 6:
                    source = "【赞皇】";
                    break;
                case 7:
                    source = "【鹿泉】";
                    break;
                case 8:
                    source = "【灵寿】";
                    break;
            }
            mapa.put("info", "A、" + source + "补换卡信息提交成功");
            mapb.put("info", "B、" + source + "制卡信息网上初审");
            mapc.put("info", "C、" + source + "制卡信息导出到省级制卡平台");

            resultList.add(mapa);
            resultList.add(mapb);
            resultList.add(mapc);

            if (mapc.get("flag").toString().equals("1")) {
                // 社保卡基本信息查询
                Result result = sbkService.getResult("0811014", zkjdcxParam.getSfzh() + "||");
                if (!"200".equals(result.getStatusCode())) {
                    throw new ServiceException(result.getMessage());
                }
                Map<String, String> data = (Map<String, String>) result.getData();
                String jbxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
                String[] jbxxcxArr = jbxxcx.split("\\|");

                String oldCardCode1 = "1、制卡信息采集已审核通过（工作日当天12点前审核通过下午寄出，12点后审核通过第二个工作日寄出）。";
                String oldCardCode2 = "2、正在写入社保信息，请耐心等待";
                String oldCardCode3 = "3、制卡成功，待邮寄";
                switch (state) {
                    case "32":
                    case "33": {
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("flag", 1);
                        map1.put("info", oldCardCode1);
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("flag", 1);
                        map2.put("info", oldCardCode2);
                        Map<String, Object> map3 = new HashMap<>();
                        map3.put("flag", 1);
                        map3.put("info", oldCardCode3);

                        map1.put("time_flag", 0);
                        map2.put("time_flag", 0);
                        map3.put("time_flag", 0);

                        if ("2".equals(jbxxcxArr[14])) {
                            map1.put("flag", 0);
                            map2.put("flag", 0);
                            map3.put("flag", 0);
                        }

                        mapList.add(map1);
                        mapList.add(map2);
                        mapList.add(map3);
                        break;
                    }
                    case "11": {
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("flag", 1);
                        map1.put("info", oldCardCode1);
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("flag", 0);
                        map2.put("info", oldCardCode2);
                        Map<String, Object> map3 = new HashMap<>();
                        map3.put("flag", 0);
                        map3.put("info", oldCardCode3);

                        map1.put("time_flag", 0);
                        map2.put("time_flag", 0);
                        map3.put("time_flag", 0);

                        if ("2".equals(jbxxcxArr[14])) {
                            map1.put("flag", 0);
                        }

                        mapList.add(map1);
                        mapList.add(map2);
                        mapList.add(map3);
                        break;
                    }
                    default: {
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("flag", 0);
                        map1.put("info", oldCardCode1);
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("flag", 0);
                        map2.put("info", oldCardCode2);
                        Map<String, Object> map3 = new HashMap<>();
                        map3.put("flag", 0);
                        map3.put("info", oldCardCode3);

                        map1.put("time_flag", 0);
                        map2.put("time_flag", 0);
                        map3.put("time_flag", 0);
                        mapList.add(map1);
                        mapList.add(map2);
                        mapList.add(map3);
                        break;
                    }
                }


                Map<String, Object> map4 = new HashMap<>();
                map4.put("info", "4、已邮寄");
                LambdaQueryWrapper<WxSmspersonEms> queryWrapper = new LambdaQueryWrapper<WxSmspersonEms>()
                        .like(WxSmspersonEms::getIdcard, wxBukaInfo.getIdcardno())
                        .ge(WxSmspersonEms::getImporttime, wxBukaInfo.getExamineTime());
                WxSmspersonEms wxSmspersonEms = wxSmspersonEmsService.selectOneByLambdaQueryWrapper(queryWrapper);
                if (wxSmspersonEms == null) {
                    map4.put("flag", 0);
                } else {
                    map4.put("flag", 1);
                    map4.put("mailnum", wxSmspersonEms.getMailnum());
                }


//                Map<String, Object> map5 = new HashMap<>();
//                map5.put("info", "个人已领取社保卡");
//                if (wxBukaInfo.getMailStatus() == 2 || wxBukaInfo.getMailStatus() == 3) {
//                    map5.put("flag", 1);
//                } else {
//                    map5.put("flag", 0);
//                }

                map4.put("time_flag", 0);
//                map5.put("time_flag", 0);

                mapList.add(map4);
//                mapList.add(map5);

                resultList.addAll(mapList);
            }

            buhuankaMap.put("data", resultList);

            buhuankaList.add(buhuankaMap);
        }
        return buhuankaList;
    }
}
