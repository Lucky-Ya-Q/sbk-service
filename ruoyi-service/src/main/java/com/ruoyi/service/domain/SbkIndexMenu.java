package com.ruoyi.service.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 首页菜单对象 sbk_index_menu
 *
 * @author lucky-ya-q
 * @date 2022-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SbkIndexMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId
    private Long menuId;

    /**
     * 菜单图标
     */
    @Excel(name = "菜单图标")
    private String icon;

    /**
     * 菜单名称
     */
    @Excel(name = "菜单名称")
    private String menuName;

    /**
     * 路由地址
     */
    @Excel(name = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Excel(name = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Excel(name = "路由参数")
    private String param;

    /**
     * 是否为外链（0是 1否）
     */
    @Excel(name = "是否为外链", readConverterExp = "0=是,1=否")
    private Integer isFrame;

    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @Excel(name = "菜单状态", readConverterExp = "0=显示,1=隐藏")
    private String visible;
}
