package com.ruoyi.service.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户信息对象 sbk_customer
 * 
 * @author lucky-ya-q
 * @date 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SbkCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 补卡ID */
    private Long bukaId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String xm;

    /** 证件种类 */
    @Excel(name = "证件种类")
    private String zjzl;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String zjhm;

    /** 证件有效期开始时间 */
    @Excel(name = "证件有效期开始时间")
    private String zjyxqkssj;

    /** 证件有效期结束时间 */
    @Excel(name = "证件有效期结束时间")
    private String zjyxqjssj;

    /** 性别 */
    @Excel(name = "性别")
    private String xb;

    /** 国籍 */
    @Excel(name = "国籍")
    private String gj;

    /** 民族 */
    @Excel(name = "民族")
    private String mz;

    /** 出生日期 */
    @Excel(name = "出生日期")
    private String csrq;

    /** 职业 */
    @Excel(name = "职业")
    private String zy;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String lxfs;

    /** 地址 */
    @Excel(name = "地址")
    private String dz;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String yhmc;

    /** 是否已补卡 */
    @Excel(name = "是否已补卡", type = Excel.Type.EXPORT)
    private String sfybk;
}
