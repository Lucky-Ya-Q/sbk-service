package com.ruoyi.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Result {
    private String statusCode;
    private String message;
    private Object data;
}