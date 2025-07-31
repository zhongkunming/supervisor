package com.unknown.supervisor.portal.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 删除菜单VO
 *
 * @author zhongkunming
 */
@Data
public class SysMenuDeleteInputVO {

    /**
     * 主键ID列表
     */
    @NotEmpty(message = "菜单ID列表不能为空")
    @Schema(description = "菜单ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> ids;
}