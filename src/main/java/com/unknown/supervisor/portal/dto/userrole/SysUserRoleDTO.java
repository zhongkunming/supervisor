package com.unknown.supervisor.portal.dto.userrole;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色关联DTO
 *
 * @author zhongkunming
 */
@Data
public class SysUserRoleDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 操作员号
     */
    private String operNo;

    /**
     * 角色编码
     */
    private String roleCode;

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
}