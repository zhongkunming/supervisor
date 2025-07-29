package com.unknown.supervisor.portal.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录输出VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "登录输出信息")
public class LoginOutputVO {

    @Schema(description = "JWT令牌")
    private String token;

    @Schema(description = "操作员编号")
    private String operNo;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;
}