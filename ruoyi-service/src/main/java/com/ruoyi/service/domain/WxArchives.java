package com.ruoyi.service.domain;

import lombok.Data;

import java.util.Date;

@Data
public class WxArchives {
    /**
     *
     */
    private Integer id;

    /**
     * 步骤状态9代表完成
     */
    private String stepStatus;

    /**
     * 数据来源：1微信公众号  2：12333网站
     */
    private String source;

    /**
     * 检索码
     */
    private String personid;

    /**
     * 申请人姓名
     */
    private String name;

    /**
     *
     */
    private String openid;

    /**
     *
     */
    private String nickname;

    /**
     * 身份证号码
     */
    private String cardNum;

    /**
     * 通讯地址
     */
    private String communicationAddress;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 电话
     */
    private String phone;

    /**
     * 证件类型 1代表身份证 2代表户口本
     */
    private String cardType;

    /**
     * 身份证有效期结束时间
     */
    private Date endTime;

    /**
     * 性别 0代表女   1代表男
     */
    private String sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 民族
     */
    private String nation;

    /**
     * 是否成年0代表是成年人    1代表是未成年人
     */
    private String isAdult;

    /**
     * 监护人姓名
     */
    private String guardianName;

    /**
     * 监护人身份证号码
     */
    private String guardianCardNum;

    /**
     * 监护人电话
     */
    private String guardianPhone;

    /**
     * 监护人证件类型
     */
    private String guardianCardType;

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
     *
     */
    private String centercode;

    /**
     *
     */
    private String unitcode;

    /**
     *
     */
    private String unitcodeDs;

    /**
     * 单位
     */
    private String company;

    /**
     * 是否邮寄 0 代表不邮寄  1代表邮寄
     */
    private String isMail;

    /**
     *
     */
    private Date addTime;

    /**
     * 是否导出信息 0 代表否 ，1代表是
     */
    private String isDownInfo;

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
     * zhifu:打赏; haopin:好评
     */
    private String finish;

    /**
     * 完成时间
     */
    private Integer timeEnd;

    /**
     *
     */
    private String lng;

    /**
     *
     */
    private String lat;

    /**
     *
     */
    private String bank;

    /**
     *
     */
    private String formID;

    /**
     *
     */
    private String lingka;

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
     * 区县编码
     */
    private String countyCode;

    /**
     * 合并订单号
     */
    private String combinedOrderno;

    /**
     * 0未合并  1已合并
     */
    private Integer combinedFlag;

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
     * 户口性质编号
     */
    private String residenceTypeId;

    /**
     * 户口所在地
     */
    private String residenceAddress;

    /**
     * 职业编号
     */
    private String occupationId;

    /**
     * 证件开始时间
     */
    private Date beginTime;

    /**
     * 代办人关系编号
     */
    private String daiRelationId;

    /**
     * 代办人性别
     */
    private String daiSex;

    /**
     * 代办人证件开始时间
     */
    private Date daiBeginTime;

    /**
     * 代办人证件结束时间
     */
    private Date daiEndTime;

    /**
     * 代办人民族编号
     */
    private String daiNationId;

    /**
     * 代办人户口所在地区县编码
     */
    private Integer daiCountyCode;

    /**
     * 收件人地址
     */
    private String communicationAddressMail;

    /**
     * 收件人详细地址
     */
    private String detailedAddressMail;

    /**
     * 收件人电话
     */
    private String phoneMail;

    /**
     * 收件人区县编码
     */
    private String countyCodeMail;

    /**
     * 银行ID
     */
    private Integer bankid;
}

