package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统角色删除输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统角色删除输入VO")
public class SysRoleDeleteInputVO {

    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "角色ID不能为空")
    private Long id;
}