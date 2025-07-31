package com.unknown.supervisor.portal.vo.rolemenu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取角色菜单关联信息VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "获取角色菜单关联信息VO")
public class SysRoleMenuGetInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "角色菜单关联ID不能为空")
    @Schema(description = "角色菜单关联ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}