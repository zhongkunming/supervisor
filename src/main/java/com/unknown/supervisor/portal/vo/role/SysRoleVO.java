package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统角色信息")
public class SysRoleVO {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色描述")
    private String roleDesc;

    @Schema(description = "状态 1-启用 0-禁用")
    private Integer status;

    @Schema(description = "排序")
    private Integer sortOrder;

    @Schema(description = "创建操作员")
    private String createOperNo;

    @Schema(description = "创建时间")
    private LocalDateTime createDt;

    @Schema(description = "更新操作员")
    private String updateOperNo;

    @Schema(description = "更新时间")
    private LocalDateTime updateDt;
}