package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 查询用户角色输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "查询用户角色输入")
public class SysUserRoleQueryInputVO {

    /**
     * 操作员编号
     */
    @NotBlank(message = "操作员编号不能为空")
    @Schema(description = "操作员编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String operNo;
}