package com.ruoyi.service.domain;

import lombok.Data;

import java.util.Date;

@Data
public class WxArchivesStatus {
    /**
     *
     */
    private Integer id;

    /**
     * 数据来源：1微信公众号 3支付宝 5电子社保卡
     */
    private String source;

    /**
     * 步骤状态9代表完成
     */
    private String stepStatus;

    /**
     * 身份证号码
     */
    private String cardNum;

    /**
     *
     */
    private String openid;

    /**
     * 审核状态：0代表未审核 1代表审核通过 2代表审核未通过
     */
    private String examineStatus;

    /**
     * 审核时间
     */
    private Date examineTime;

    /**
     * 审核工作人员ID
     */
    private Integer examineCid;

    /**
     * 审核不通过的原因
     */
    private String reason;

    /**
     * 行政区划
     */
    private String centercode;

    /**
     * 是否邮寄 0 代表不邮寄  1代表邮寄
     */
    private String isMail;

    /**
     * 是否导出信息 0 代表否 ，1代表是
     */
    private String isDownInfo;

    /**
     * 导出时间
     */
    private Date daochuTime;

    /**
     * 是否导出照片 0 代表否 ，1代表是
     */
    private String isDownImg;

    /**
     *
     */
    private String isJpg;

    /**
     *
     */
    private Date jpgAddTime;

    /**
     * 订单号
     */
    private String orderno;

    /**
     * 邮寄类型 0 到付 1线上付
     */
    private Integer mailtype;

    /**
     * 0代表未支付  1代表支付
     */
    private Integer isZhifu;

    /**
     * 支付时间
     */
    private Date zhifuTime;

    /**
     * 申请退费原因
     */
    private String returnReason;

    /**
     * 申请退费时间
     */
    private Date returnTime;

    /**
     * 0未申请  1已申请
     */
    private Integer returnFlag;

    /**
     * 0未审核  1已通过 2已驳回
     */
    private Integer examineReturnFlag;

    /**
     * 审核退费时间
     */
    private Date examineReturnTime;

    /**
     * 驳回申请退费原因
     */
    private String examineReturnReason;

    /**
     * 退费申请操作员
     */
    private Integer examineReturnCid;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 银行ID
     */
    private Integer bankid;

    /**
     * 三代卡上传标识 0未上传 1已上传 -1老数据
     */
    private Integer uploadflag;

    /**
     * 是否生成证件照 0未生成1已生成
     */
    private Integer cutpicflag;

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
     * 工行联名卡号
     */
    private String icbcMediumId;

    /**
     * 制卡进度
     */
    private String cardStep;

    /**
     * 邮寄费免费标识 0不免费1免费
     */
    private Integer nopayflagEms;
}

