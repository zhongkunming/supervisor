package com.unknown.supervisor.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色菜单关联实体类
 *
 * @author zhongkunming
 */
@Data
@TableName("sys_role_menu")
@Schema(description = "系统角色菜单关联实体")
public class SysRoleMenu {

    @TableId(type = IdType.NONE)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "菜单编码")
    private String menuCode;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建操作员编号")
    private String createOperNo;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新操作员编号")
    private String updateOperNo;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateDt;
}