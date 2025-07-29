package com.unknown.supervisor.portal.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录DTO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "登录信息")
public class LoginInputDTO {

    @Schema(description = "操作员编号")
    @NotBlank(message = "操作员编号不能为空")
    private String operNo;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}