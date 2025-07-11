package com.unknown.supervisor.web.portal.vo.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhongkunming
 */
@Data
public class UserPageOutputVo {

    @Schema(description = "用户名")
    private String username;
}
