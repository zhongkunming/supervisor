package com.unknown.supervisor.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色关联实体类
 *
 * @author zhongkunming
 */
@Data
@TableName("sys_user_role")
public class SysUserRole {

    /**
     * 主键ID
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 操作员编号
     */
    private String operNo;

    /**
     * 角色编码
     */
    private String roleCode;

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