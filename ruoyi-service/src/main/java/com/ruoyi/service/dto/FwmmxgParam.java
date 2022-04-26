package com.ruoyi.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 服务密码修改参数
 */
@Data
public class FwmmxgParam {
    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty("旧密码")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty("新密码")
    private String newPassword;
}
