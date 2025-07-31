package com.unknown.supervisor.portal.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录响应VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "登录响应VO")
public class LoginOutputVO {

    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌")
    private String token;
}