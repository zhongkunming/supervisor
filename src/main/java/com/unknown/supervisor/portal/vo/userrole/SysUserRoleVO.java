package com.unknown.supervisor.portal.vo.userrole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色关联VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "用户角色关联VO")
public class SysUserRoleVO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 操作员号
     */
    @Schema(description = "操作员号")
    private String operNo;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createDt;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateDt;
}