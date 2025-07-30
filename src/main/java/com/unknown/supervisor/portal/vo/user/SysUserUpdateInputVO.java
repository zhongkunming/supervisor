package com.unknown.supervisor.portal.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统用户更新输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统用户更新输入信息")
public class SysUserUpdateInputVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "状态（1-启用，0-禁用）")
    private Integer status;
}