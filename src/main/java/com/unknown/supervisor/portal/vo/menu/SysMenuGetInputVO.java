package com.unknown.supervisor.portal.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取菜单信息VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "获取菜单信息VO")
public class SysMenuGetInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "菜单ID不能为空")
    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}