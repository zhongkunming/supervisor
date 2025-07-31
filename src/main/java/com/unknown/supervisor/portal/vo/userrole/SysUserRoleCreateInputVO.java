package com.unknown.supervisor.portal.vo.userrole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建用户角色关联VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "创建用户角色关联VO")
public class SysUserRoleCreateInputVO {

    /**
     * 操作员号
     */
    @NotBlank(message = "操作员号不能为空")
    @Schema(description = "操作员号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String operNo;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleCode;
}