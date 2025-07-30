package com.unknown.supervisor.portal.dto.role;

import lombok.Data;

/**
 * 用户角色查询输出DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
public class SysUserRoleQueryDTO {

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
     * 状态（1-启用，0-禁用）
     */
    private Integer status;
}