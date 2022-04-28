package com.ruoyi.service.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 补卡上传记录对象 sbk_upload_buka
 * 
 * @author lucky-ya-q
 * @date 2022-04-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SbkUploadBuka extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 补卡ID */
    @TableId
    private Long bukaId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
