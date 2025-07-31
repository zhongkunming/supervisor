package com.unknown.supervisor.portal.dto.menu;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜单权限DTO
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
    private String code;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单编码
     */
    private String pcode;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 是否组件
     */
    private Boolean isFrame;

    /**
     * 是否缓存
     */
    private Boolean isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String type;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 创建人
     */
    private String createOperNo;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新人
     */
    private String updateOperNo;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 备注
     */
    private String remark;
}