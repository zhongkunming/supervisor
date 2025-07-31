package com.unknown.supervisor.portal.vo.userrole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 删除用户角色关联VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "删除用户角色关联VO")
public class SysUserRoleDeleteInputVO {

    /**
     * 主键ID列表
     */
    @NotEmpty(message = "用户角色关联ID列表不能为空")
    @Schema(description = "用户角色关联ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> ids;
}