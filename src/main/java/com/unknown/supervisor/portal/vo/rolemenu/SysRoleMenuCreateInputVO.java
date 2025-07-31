package com.unknown.supervisor.portal.vo.rolemenu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建角色菜单关联VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "创建角色菜单关联VO")
public class SysRoleMenuCreateInputVO {

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