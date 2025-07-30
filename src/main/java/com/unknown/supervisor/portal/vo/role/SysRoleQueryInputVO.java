package com.unknown.supervisor.portal.vo.role;

import com.unknown.supervisor.core.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色查询输入VO
 *
 * @author zhongkunming
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统角色查询输入信息")
public class SysRoleQueryInputVO extends PageRequest {

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "状态（1-启用，0-禁用）")
    private Integer status;
}