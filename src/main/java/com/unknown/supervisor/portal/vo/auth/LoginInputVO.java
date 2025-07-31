package com.unknown.supervisor.portal.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "登录VO")
public class LoginInputVO {

    /**
     * 操作员号
     */
    @NotBlank(message = "操作员号不能为空")
    @Schema(description = "操作员号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String operNo;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}