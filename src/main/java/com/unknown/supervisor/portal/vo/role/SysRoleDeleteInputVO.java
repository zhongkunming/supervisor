package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 删除角色VO
 *
 * @author zhongkunming
 */
@Data
public class SysRoleDeleteInputVO {

    /**
     * 主键ID列表
     */
    @NotEmpty(message = "角色ID列表不能为空")
    @Schema(description = "角色ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> ids;
}