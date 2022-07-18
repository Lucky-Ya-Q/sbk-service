package com.ruoyi.service.domain;

import java.util.Date;
import lombok.Data;

@Data
public class UnitinfoShi {
    /**
     * 单位编号
     */
    private String unitcode;

    /**
     * 区域编号
     */
    private String centercode;

    /**
     * 单位名称
     */
    private String unitname;

    /**
     * 单位名称说明
     */
    private String tips;

    /**
     * 0:禁用 1：启用
     */
    private Integer flag;

    /**
     * 单位类型
     */
    private String unittype;

    /**
     * 是否允许选择区域网点发卡
     */
    private Integer flagQuxian;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     *
     */
    private Integer cid;
}

