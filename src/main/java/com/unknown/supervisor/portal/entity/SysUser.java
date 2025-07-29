package com.unknown.supervisor.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户实体类
 *
 * @author zhongkunming
 */
@Data
@TableName("sys_user")
@Schema(description = "系统用户实体")
public class SysUser {

    @TableId(type = IdType.INPUT)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "操作员编号")
    private String operNo;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "状态 1-启用 0-禁用")
    private Integer status;

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