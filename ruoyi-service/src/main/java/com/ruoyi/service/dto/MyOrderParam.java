package com.ruoyi.service.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MyOrderParam {
    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String sfzh;

    /**
     * 收件人手机号
     */
    @ApiModelProperty("收件人手机号")
    private String mobile;

    /**
     * 类型
     */
    @ApiModelProperty("1待寄出 2我寄出 3 我收到")
    private Long type;
}
