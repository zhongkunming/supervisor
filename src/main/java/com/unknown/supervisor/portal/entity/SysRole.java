package com.unknown.supervisor.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色实体类
 *
 * @author zhongkunming
 */
@Data
@TableName("sys_role")
public class SysRole {

    /**
     * 主键ID
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sortOrder;

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