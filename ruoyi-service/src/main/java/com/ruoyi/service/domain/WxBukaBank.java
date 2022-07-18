package com.ruoyi.service.domain;

import java.util.Date;
import lombok.Data;

@Data
public class WxBukaBank {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String code;

    /**
     *
     */
    private Date addTime;

    /**
     * 添加人ID
     */
    private Integer addId;

    /**
     * 排序
     */
    private Integer orderId;

    /**
     * 1启用   2不启用
     */
    private Integer flag;

    /**
     * 1全省通办 0石家庄
     */
    private Integer provinceflag;

    /**
     * 三代卡系统银行编号
     */
    private String bankcode;

    /**
     * 是否免邮寄费 0不免费1免费
     */
    private Integer nopayflag;

    /**
     * 提示信息
     */
    private String info;

    /**
     * 提示信息是否显示0不显示1显示
     */
    private Integer infoflag;
}

