package com.ruoyi.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 续借记录对象 sbk_library_renew
 * 
 * @author lucky-ya-q
 * @date 2022-06-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SbkLibraryRenew extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 读者证号 */
    @Excel(name = "读者证号")
    private String rdid;

    /** 提名 */
    @Excel(name = "提名")
    private String title;

    /** 条码号 */
    @Excel(name = "条码号")
    private String barcode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
