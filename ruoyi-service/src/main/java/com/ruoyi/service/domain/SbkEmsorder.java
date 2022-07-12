package com.ruoyi.service.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 邮政订单对象 sbk_emsorder
 *
 * @author lucky-ya-q
 * @date 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SbkEmsorder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String sfzh;

    /**
     * 收件人手机号
     */
    @Excel(name = "收件人手机号")
    private String mobile;

    /**
     * 揽收状态
     */
    @Excel(name = "揽收状态")
    private Long status;

    /**
     * 物流订单号
     */
    @Excel(name = "物流订单号")
    private String logisticsOrderNo;

    /**
     * 内件性质
     */
    @Excel(name = "内件性质")
    private Long contentsAttribute;

    /**
     * 业务产品分类
     */
    @Excel(name = "业务产品分类")
    private Long bizProductNo;

    /**
     * 订单重量
     */
    @Excel(name = "订单重量")
    private Double weight;

    /**
     * 投递方式
     */
    @Excel(name = "投递方式")
    private Long deliverType;

    /**
     * 付款方式
     */
    @Excel(name = "付款方式")
    private Long paymentMode;

    /**
     * 带包装
     */
    @Excel(name = "带包装")
    private String daibaozhuang;

    /**
     * 预约时间
     */
    @Excel(name = "预约时间")
    private String subTime;

    /**
     * 客户类型
     */
    @Excel(name = "客户类型")
    private Long senderType;

    /**
     * 协议客户代码
     */
    @Excel(name = "协议客户代码")
    private String senderNo;

    /**
     * 留言
     */
    @Excel(name = "留言")
    private String message;

    /**
     * 推送方式
     */
    @Excel(name = "推送方式")
    private Long pushMethod;

    /**
     * 寄件人信息
     */
    @Excel(name = "寄件人信息")
    private String sender;

    /**
     * 收件人信息
     */
    @Excel(name = "收件人信息")
    private String receiver;

    /**
     * 上门取件人信息
     */
    @Excel(name = "上门取件人信息")
    private String delivery;

    /**
     * 派揽状态
     */
    @Excel(name = "派揽状态")
    private String worker;

    /**
     * 揽收结果
     */
    @Excel(name = "揽收结果")
    private String result;
}
