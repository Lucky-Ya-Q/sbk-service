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
public class SbkEmsorder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 物流订单号 */
    @Excel(name = "物流订单号")
    private String logisticsOrderNo;

    /** 业务产品分类 */
    @Excel(name = "业务产品分类")
    private Long bizProductNo;

    /** 客户类型 */
    @Excel(name = "客户类型")
    private Long senderType;

    /** 推送方式 */
    @Excel(name = "推送方式")
    private Long pushMethod;

    /** 寄件人信息 */
    @Excel(name = "寄件人信息")
    private String sender;

    /** 收件人信息 */
    @Excel(name = "收件人信息")
    private String receiver;

    /** 上门取件人信息 */
    @Excel(name = "上门取件人信息")
    private String delivery;
}
