package com.unknown.supervisor.portal.dto.role;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色DTO
 *
 * @author zhongkunming
 */
@Data
public class SysRoleDTO {

    /**
     * 主键ID
     */
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
}