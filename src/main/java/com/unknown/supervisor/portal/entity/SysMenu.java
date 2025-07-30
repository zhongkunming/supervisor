package com.unknown.supervisor.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统菜单实体类
 *
 * @author zhongkunming
 */
@Data
@TableName("sys_menu")
public class SysMenu {

    /**
     * 主键ID
     */
    @TableId(type = IdType.NONE)
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
    @TableField(fill = FieldFill.INSERT)
    private String createOperNo;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDt;

    /**
     * 更新操作员编号
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateOperNo;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDt;
}