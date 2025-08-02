package com.unknown.supervisor.portal.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜单VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "菜单VO")
public class SysMenuVO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 菜单编码
     */
    @Schema(description = "菜单编码")
    private String code;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 父菜单编码
     */
    @Schema(description = "父菜单编码")
    private String pcode;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    private String query;

    /**
     * 路由名称
     */
    @Schema(description = "路由名称")
    private String routeName;

    /**
     * 是否内链
     */
    @Schema(description = "是否内链")
    private Boolean isFrame;

    /**
     * 是否缓存
     */
    @Schema(description = "是否缓存")
    private Boolean isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @Schema(description = "菜单类型（M目录 C菜单 F按钮）")
    private String type;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @Schema(description = "菜单状态（0显示 1隐藏）")
    private String visible;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 菜单状态（0正常 1停用）
     */
    @Schema(description = "菜单状态（0正常 1停用）")
    private String status;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer orderNum;

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

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}