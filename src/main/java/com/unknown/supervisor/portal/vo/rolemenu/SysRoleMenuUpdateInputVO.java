package com.unknown.supervisor.portal.vo.rolemenu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新角色菜单关联VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "更新角色菜单关联VO")
public class SysRoleMenuUpdateInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "角色菜单关联ID不能为空")
    @Schema(description = "角色菜单关联ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleCode;

    /**
     * 菜单编码
     */
    @NotBlank(message = "菜单编码不能为空")
    @Schema(description = "菜单编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String menuCode;
}