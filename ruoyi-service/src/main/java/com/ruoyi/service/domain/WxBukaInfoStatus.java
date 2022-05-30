package com.ruoyi.service.domain;

import java.util.Date;
import lombok.Data;

@Data
public class WxBukaInfoStatus {
    /**
     *
     */
    private Integer id;

    /**
     * 身份证号
     */
    private String idcardno;

    /**
     * 生成的订单号
     */
    private String orderno;

    /**
     * 工行e钱包绑定卡号
     */
    private String icbcBindMedium;

    /**
     * 0:未同意开通 1:已开通 2：同意未开通
     */
    private Integer icbcFlag;

    /**
     * 工行钱包开通途径 0:H5 1:API
     */
    private Integer icbcType;

    /**
     * 开通工行钱包减免金额
     */
    private Integer icbcMoneyDiscount;

    /**
     * 开通工行钱包后选择工行卡减免金额
     */
    private Integer icbcMoneyDiscountIcbccard;

    /**
     * 邮寄费
     */
    private Integer moneyEms;

    /**
     * 工行钱包开通时间
     */
    private Date icbcTime;

    /**
     * 工行钱包标识
     */
    private String icbcUserid;

    /**
     * 是否取消开通 0未取消1取消
     */
    private Integer icbcOpencancel;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 三代卡上传标识 0未上传 1已上传
     */
    private Integer uploadflag;

    /**
     * 三代卡快速制卡批次号
     */
    private String batchNum;

    /**
     * 工行联名卡号
     */
    private String icbcMediumId;

    /**
     * 邮寄费免费标识 0不免费1免费
     */
    private Integer nopayflagEms;

    /**
     * 是否生成业务单标识 0未生成1已生成2已导出
     */
    private Integer isJpg;

    /**
     * 业务单生成时间
     */
    private Date jpgAddTime;
}

