package com.unknown.supervisor.portal.vo.userrole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取用户角色关联信息VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "获取用户角色关联信息VO")
public class SysUserRoleGetInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "用户角色关联ID不能为空")
    @Schema(description = "用户角色关联ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}