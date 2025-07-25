package com.unknown.supervisor.portal.vo.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author zhongkunming
 */
@Data
public class UserDeleteInputVo {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
}
