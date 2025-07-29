package com.unknown.supervisor.portal.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录响应DTO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "登录响应信息")
public class LoginOutputDTO {

    @Schema(description = "JWT令牌")
    private String token;

    @Schema(description = "操作员编号")
    private String operNo;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;
}