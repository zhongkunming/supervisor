package com.unknown.supervisor.portal.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "角色VO")
public class SysRoleVO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String code;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色权限字符串
     */
    @Schema(description = "角色权限字符串")
    private String key;

    /**
     * 角色状态（0正常 1停用）
     */
    @Schema(description = "角色状态（0正常 1停用）")
    private String status;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer orderNum;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private Boolean isDelete;

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

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}