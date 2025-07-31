package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取角色信息VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "获取角色信息VO")
public class SysRoleGetInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}