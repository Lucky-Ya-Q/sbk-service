package com.ruoyi.service.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 西柏坡订单对象 sbk_xbporder
 *
 * @author lucky-ya-q
 * @date 2022-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SbkXbporder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId
    private Long orderId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idCard;

    /**
     * 游客信息
     */
    @Excel(name = "游客信息")
    private String touristInfos;

    /**
     * 入园日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入园日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date admissionDate;

    /**
     * 入园时段
     */
    @Excel(name = "入园时段")
    private String admissionPeriod;

    /**
     * 门票价格
     */
    @Excel(name = "门票价格")
    private String ticketPrice;

    /**
     * 所购门票名称
     */
    @Excel(name = "所购门票名称")
    private String ticketsPurchasedName;
}
