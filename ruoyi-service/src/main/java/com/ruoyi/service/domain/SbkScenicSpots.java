package com.ruoyi.service.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 旅游景点对象 sbk_scenic_spots
 * 
 * @author lucky-ya-q
 * @date 2022-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SbkScenicSpots extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 景点ID */
    @TableId
    private Long spotsId;

    /** 相册 */
    @Excel(name = "相册")
    private String photoAlbum;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 路由地址 */
    @Excel(name = "路由地址")
    private String path;

    /** 按钮组 */
    @Excel(name = "按钮组")
    private String buttonGroup;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 官方通知 */
    private String notice;

    /** 服务热线 */
    @Excel(name = "服务热线")
    private String phone;

    /** 图文介绍 */
    private String introduce;
}
