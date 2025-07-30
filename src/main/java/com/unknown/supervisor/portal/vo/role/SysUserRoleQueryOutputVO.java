package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询用户角色输出VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "用户角色信息")
public class SysUserRoleQueryOutputVO {

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String roleDesc;

    /**
     * 状态 1-启用 0-禁用
     */
    @Schema(description = "状态 1-启用 0-禁用")
    private Integer status;
}