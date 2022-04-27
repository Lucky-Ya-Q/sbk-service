package com.ruoyi.service.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

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
}
