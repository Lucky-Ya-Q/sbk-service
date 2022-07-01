package com.ruoyi.service.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 邮政地址簿对象 sbk_ems_address
 *
 * @author lucky-ya-q
 * @date 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SbkEmsAddress extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号")
    private String sfzh;

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
     * 省
     */
    @Excel(name = "省")
    private String prov;

    /**
     * 市
     */
    @Excel(name = "市")
    private String city;

    /**
     * 区县
     */
    @Excel(name = "区县")
    private String county;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 是否默认
     */
    @Excel(name = "是否默认")
    private Long isDefault;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private Long type;
}
