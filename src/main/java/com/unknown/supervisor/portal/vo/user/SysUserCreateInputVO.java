package com.unknown.supervisor.portal.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统用户创建输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统用户创建输入信息")
public class SysUserCreateInputVO {

    @Schema(description = "操作员编号")
    @NotBlank(message = "操作员编号不能为空")
    private String operNo;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "状态（1-启用，0-禁用）")
    @NotNull(message = "状态不能为空")
    private Integer status;
}