package com.unknown.supervisor.portal.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统菜单查询输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统菜单查询输入VO")
public class SysMenuGetInputVO {

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "菜单ID不能为空")
    private Long id;
}