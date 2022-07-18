package com.ruoyi.service.domain;

import lombok.Data;

@Data
public class WxOccupation {
    /**
     *
     */
    private String id;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 工行职业编号
     */
    private Integer icbcid;
}

