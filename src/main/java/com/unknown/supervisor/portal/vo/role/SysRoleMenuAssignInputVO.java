package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 系统角色菜单分配输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统角色菜单分配输入信息")
public class SysRoleMenuAssignInputVO {

    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @Schema(description = "菜单编码列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "菜单编码列表不能为空")
    private List<String> menuCodes;
}