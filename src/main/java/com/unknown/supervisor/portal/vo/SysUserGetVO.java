package com.unknown.supervisor.portal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取用户信息VO
 *
 * @author zhongkunming
 */
@Data
public class SysUserGetVO {

    /**
     * 主键ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}