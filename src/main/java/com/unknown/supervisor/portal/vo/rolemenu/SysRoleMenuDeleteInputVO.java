package com.unknown.supervisor.portal.vo.rolemenu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 删除角色菜单关联VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "删除角色菜单关联VO")
public class SysRoleMenuDeleteInputVO {

    /**
     * 主键ID列表
     */
    @NotEmpty(message = "角色菜单关联ID列表不能为空")
    @Schema(description = "角色菜单关联ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> ids;
}