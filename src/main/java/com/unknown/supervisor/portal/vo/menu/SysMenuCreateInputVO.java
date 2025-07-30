package com.unknown.supervisor.portal.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统菜单创建输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统菜单创建输入信息")
public class SysMenuCreateInputVO {

    @Schema(description = "菜单编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "菜单编码不能为空")
    private String menuCode;

    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    @Schema(description = "父菜单ID")
    private Long pid;

    @Schema(description = "菜单类型 1-目录 2-菜单 3-按钮", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;

    @Schema(description = "菜单路径")
    private String menuPath;

    @Schema(description = "菜单组件")
    private String component;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "权限标识")
    private String permission;

    @Schema(description = "状态 1-启用 0-禁用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "排序")
    private Integer sortOrder;

    @Schema(description = "是否可见 1-可见 0-隐藏")
    private Integer visible;

    @Schema(description = "是否外链 1-是 0-否", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否外链不能为空")
    private Integer isExternal;
}