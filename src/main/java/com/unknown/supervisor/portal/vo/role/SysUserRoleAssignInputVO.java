package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 分配用户角色输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "分配用户角色输入")
public class SysUserRoleAssignInputVO {

    /**
     * 操作员编号
     */
    @NotBlank(message = "操作员编号不能为空")
    @Schema(description = "操作员编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String operNo;

    /**
     * 角色编码列表
     */
    @NotEmpty(message = "角色编码列表不能为空")
    @Schema(description = "角色编码列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> roleCodes;
}