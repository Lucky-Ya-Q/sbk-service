package com.ruoyi.service.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author chuxiaojiang
 * @version 1.0
 * @date 2022/5/31 14:22
 */
@Data
public class AppointmentVO {


    /**
     * 证件类型
     * 1-身份证
     * 2-护照
     * 3-护士证
     * 4-教师证
     * 5-导游证
     * 6-军官证
     * 7-通行证
     * 8-学生证
     * 9-其他
     */
    // 游客信息：游客名称name、手机号phoneNumber、证件号码identificationNumber、证件类型documentType
    private List<JSONObject> touristInfos;
    // 入园日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date admissionDate;
    // 入园时段
    private String admissionPeriod;
    // 门票价格
    private String ticketPrice;
    // 所购门票名称
    private String ticketsPurchasedName;
    // 社保卡信息解密数据：name、phone、idCard
    private JSONObject decryptData;
}
