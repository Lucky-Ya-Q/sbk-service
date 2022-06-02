package com.ruoyi.service.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.service.domain.WxArchives;
import com.ruoyi.service.domain.WxArchivesStatus;
import com.ruoyi.service.domain.WxBukaInfo;
import com.ruoyi.service.dto.ZkjdcxParam;
import com.ruoyi.service.service.SbkBaseService;
import com.ruoyi.service.service.WxArchivesService;
import com.ruoyi.service.service.WxArchivesStatusService;
import com.ruoyi.service.service.WxBukaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SbkBaseServiceImpl implements SbkBaseService {
    @Autowired
    private WxArchivesService wxArchivesService;
    @Autowired
    private WxArchivesStatusService wxArchivesStatusService;
    @Autowired
    private WxBukaInfoService wxBukaInfoService;

    @Override
    public Map<String, Object> getShenLingData(ZkjdcxParam zkjdcxParam, String state) {
        // shenling
        Map<String, Object> shenlingMap = new HashMap<>();
        // shenling.data 所有数据
        List<Map<String, Object>> resultList = new ArrayList<>();
        // shenling.data 德生数据
        List<Map<String, Object>> mapList = new ArrayList<>();

        String newCardCode1 = "1、制卡信息采集已审核通过（审核通过后五个工作日完成制卡，请耐心等待）。";
        String newCardCode2 = "2、正在写入社保信息，请耐心等待";
        String newCardCode3 = "3、制卡成功，卡中心分卡、验卡、入库";
        String newCardCode4 = "4、等待县区网点或单位、社区领卡";
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
                map4.put("flag", 1);
                map4.put("info", newCardCode4);
                Map<String, Object> map5 = new HashMap<>();
                map5.put("flag", 1);
                map5.put("info", "5、个人已领取社保卡");

                map1.put("time_flag", 0);
                map2.put("time_flag", 0);
                map3.put("time_flag", 0);
                map4.put("time_flag", 0);
                map5.put("time_flag", 0);
                mapList.add(map1);
                mapList.add(map2);
                mapList.add(map3);
                mapList.add(map4);
                mapList.add(map5);
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
                map4.put("flag", 1);
                map4.put("info", newCardCode4);
                Map<String, Object> map5 = new HashMap<>();
                map5.put("flag", 1);
                map5.put("info", "5、单位已领取社保卡");

                map1.put("time_flag", 0);
                map2.put("time_flag", 0);
                map3.put("time_flag", 0);
                map4.put("time_flag", 0);
                map5.put("time_flag", 0);
                mapList.add(map1);
                mapList.add(map2);
                mapList.add(map3);
                mapList.add(map4);
                mapList.add(map5);
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
                Map<String, Object> map5 = new HashMap<>();
                map5.put("flag", 0);
                map5.put("info", "5、单位或者个人已领取社保卡");

                map1.put("time_flag", 0);
                map2.put("time_flag", 0);
                map3.put("time_flag", 0);
                map4.put("time_flag", 0);
                map5.put("time_flag", 0);
                mapList.add(map1);
                mapList.add(map2);
                mapList.add(map3);
                mapList.add(map4);
                mapList.add(map5);
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
                Map<String, Object> map5 = new HashMap<>();
                map5.put("flag", 0);
                map5.put("info", "5、单位或者个人已领取社保卡");

                map1.put("time_flag", 0);
                map2.put("time_flag", 0);
                map3.put("time_flag", 0);
                map4.put("time_flag", 0);
                map5.put("time_flag", 0);
                mapList.add(map1);
                mapList.add(map2);
                mapList.add(map3);
                mapList.add(map4);
                mapList.add(map5);
                break;
            }
        }

        WxArchives wxArchives = wxArchivesService.selectOneByLambdaQueryWrapper(new LambdaQueryWrapper<WxArchives>().eq(WxArchives::getCardNum, zkjdcxParam.getSfzh()).eq(WxArchives::getStepStatus, 9));
        if (wxArchives != null) {
            if (wxArchives.getIsMail().equals("1")) {
                mapList.get(4).put("info", "5、个人邮寄领取社保卡");
            }
        }

        WxArchivesStatus wxArchivesStatus = wxArchivesStatusService.selectOneByLambdaQueryWrapper(new LambdaQueryWrapper<WxArchivesStatus>().eq(WxArchivesStatus::getCardNum, zkjdcxParam.getSfzh()).eq(WxArchivesStatus::getStepStatus, 9).in(WxArchivesStatus::getExamineStatus, "0", "1", "2"));
        if (wxArchivesStatus != null) {
            wxArchives = wxArchivesService.selectOneByLambdaQueryWrapper(new LambdaQueryWrapper<WxArchives>().eq(WxArchives::getCardNum, zkjdcxParam.getSfzh()));

            shenlingMap.put("shijian", DateUtil.formatDateTime(wxArchives.getAddTime()));
            shenlingMap.put("qudao", wxArchives.getSource().equals("1") ? "微信公众号" : "12333网站");

            switch (wxArchivesStatus.getIsMail()) {
                case "1":
                    wxArchivesStatus.setIsMail("邮寄到家");
                    break;
                case "0":
                    wxArchivesStatus.setIsMail("网点领取“" + wxArchives.getLingkaNet() + "”");
                    break;
                case "2":
                    wxArchivesStatus.setIsMail("银行网点领取“" + wxArchives.getLingkaNet() + "”");
                    break;
            }

            Map<String, Object> mapa = new HashMap<>();
            mapa.put("flag", 1);
            mapa.put("add_time", DateUtil.formatDateTime(wxArchivesStatus.getAddTime()));
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
                    mapb.put("examine_time", DateUtil.formatDateTime(wxArchivesStatus.getExamineTime()));
                    mapb.put("msg", "初审通过");
                    break;
                case "2":
                    mapb.put("flag", 2);
                    mapb.put("status", 1);
                    mapb.put("examine_time", DateUtil.formatDateTime(wxArchivesStatus.getExamineTime()));
                    mapb.put("msg", "初审驳回。驳回原因：" + wxArchivesStatus.getReason() + "。");
                    break;
            }

            Map<String, Object> mapc = new HashMap<>();
            if (wxArchivesStatus.getIsJpg().equals("2")) {
                mapc.put("flag", 1);
                mapc.put("msg", "已导出");
                mapc.put("daochu_time", DateUtil.formatDateTime(wxArchivesStatus.getJpgAddTime()));
            } else {
                if (wxArchivesStatus.getIsMail().equals("网点领取“" + wxArchives.getLingkaNet() + "”") && wxArchivesStatus.getExamineStatus().equals("1")) {
                    mapc.put("flag", 1);
                    mapc.put("msg", "已导出");
                    mapc.put("daochu_time", DateUtil.formatDateTime(wxArchivesStatus.getJpgAddTime()));
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


        }
        resultList.addAll(mapList);

        shenlingMap.put("data", resultList);
        return shenlingMap;
    }

    @Override
    public List<Map<String, Object>> getBuHuanKaData(ZkjdcxParam zkjdcxParam, String state) {
        List<Map<String, Object>> buhuankaList = new ArrayList<>();

        List<WxBukaInfo> wxBukaInfoList = wxBukaInfoService.selectListByLambdaQueryWrapper(new LambdaQueryWrapper<WxBukaInfo>().eq(WxBukaInfo::getIdcardno, zkjdcxParam.getSfzh()).eq(WxBukaInfo::getStepStatus, 9).in(WxBukaInfo::getExamineStatus, "0", "1", "2").orderByDesc(WxBukaInfo::getId));
        for (WxBukaInfo wxBukaInfo : wxBukaInfoList) {
            // buhuanka
            Map<String, Object> buhuankaMap = new HashMap<>();
            buhuankaMap.put("shijian", DateUtil.formatDateTime(wxBukaInfo.getAddTime()));
            buhuankaMap.put("qudao", wxBukaInfo.getWebsource() == 1 ? "微信公众号" : "电子社保卡");
            // shenling.data 所有数据
            List<Map<String, Object>> resultList = new ArrayList<>();
            // shenling.data 德生数据
            List<Map<String, Object>> mapList = new ArrayList<>();

            Map<String, Object> mapa = new HashMap<>();
            mapa.put("flag", 1);
            mapa.put("add_time", DateUtil.formatDateTime(wxBukaInfo.getAddTime()));
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
                    mapb.put("examine_time", DateUtil.formatDateTime(wxBukaInfo.getExamineTime()));
                    mapb.put("msg", "初审通过");
                    break;
                case 2:
                    mapb.put("flag", 2);
                    mapb.put("status", 1);
                    mapb.put("examine_time", DateUtil.formatDateTime(wxBukaInfo.getExamineTime()));
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
                map4.put("mailnum", wxBukaInfo.getMailnum());
                if (wxBukaInfo.getMailnum().equals("0")) {
                    map4.put("flag", 0);
                } else {
                    map4.put("flag", 1);
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
