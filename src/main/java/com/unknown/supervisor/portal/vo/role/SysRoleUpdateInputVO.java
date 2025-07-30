package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统角色更新输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统角色更新输入信息")
public class SysRoleUpdateInputVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键ID不能为空")
    private Long id;

    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @Schema(description = "角色描述")
    private String roleDesc;

    @Schema(description = "状态 1-启用 0-禁用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "排序")
    private Integer sortOrder;
}