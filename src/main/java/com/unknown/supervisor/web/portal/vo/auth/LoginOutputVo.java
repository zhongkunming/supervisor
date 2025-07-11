package com.unknown.supervisor.web.portal.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhongkunming
 */
@Data
public class LoginOutputVo {

    @Schema(description = "token")
    private String token;
}
