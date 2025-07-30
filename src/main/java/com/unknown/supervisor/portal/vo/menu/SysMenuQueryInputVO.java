package com.unknown.supervisor.portal.vo.menu;

import com.unknown.supervisor.core.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统菜单查询输入VO
 *
 * @author zhongkunming
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统菜单查询输入信息")
public class SysMenuQueryInputVO extends PageRequest {

    @Schema(description = "菜单编码")
    private String menuCode;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "父菜单ID")
    private Long pid;

    @Schema(description = "菜单类型 1-目录 2-菜单 3-按钮")
    private Integer menuType;

    @Schema(description = "状态（1-启用，0-禁用）")
    private Integer status;

    @Schema(description = "是否可见 1-可见 0-隐藏")
    private Integer visible;
}