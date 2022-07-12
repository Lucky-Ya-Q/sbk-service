package com.ruoyi.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EmsResult {
    private String success;
    private String errorMsg;
    private String errorCode;
}
