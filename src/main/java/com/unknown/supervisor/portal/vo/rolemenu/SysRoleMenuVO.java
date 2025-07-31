package com.unknown.supervisor.portal.vo.rolemenu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色菜单关联VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "角色菜单关联VO")
public class SysRoleMenuVO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 菜单编码
     */
    @Schema(description = "菜单编码")
    private String menuCode;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createDt;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateDt;
}