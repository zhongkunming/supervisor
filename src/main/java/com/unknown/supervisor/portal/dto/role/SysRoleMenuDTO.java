package com.unknown.supervisor.portal.dto.role;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色菜单关联DTO
 *
 * @author zhongkunming
 */
@Data
public class SysRoleMenuDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 菜单编码
     */
    private String menuCode;

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