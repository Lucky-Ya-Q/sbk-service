package com.ruoyi.service.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlParam {
    @ApiModelProperty("身份证号")
    private String sfzh;
    private Long contentsAttribute;
    private Double weight;
    private Long deliverType;
    private Long paymentMode;
    private String daibaozhuang;
    private String createdTime;
    private String message;
    private JSONObject sender;
    private JSONObject receiver;
}
