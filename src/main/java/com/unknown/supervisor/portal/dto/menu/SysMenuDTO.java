package com.unknown.supervisor.portal.dto.menu;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统菜单DTO
 *
 * @author zhongkunming
 */
@Data
public class SysMenuDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long pid;

    /**
     * 菜单类型 1-目录 2-菜单 3-按钮
     */
    private Integer menuType;

    /**
     * 菜单路径
     */
    private String menuPath;

    /**
     * 菜单组件
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否可见 1-可见 0-隐藏
     */
    private Integer visible;

    /**
     * 是否外链 1-是 0-否
     */
    private Integer isExternal;

    /**
     * 创建操作员编号
     */
    private String createOperNo;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新操作员编号
     */
    private String updateOperNo;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 子菜单列表
     */
    private List<SysMenuDTO> children;
}