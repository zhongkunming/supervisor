package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 系统角色菜单查询输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统角色菜单查询输入VO")
public class SysRoleMenuQueryInputVO {

    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;
}