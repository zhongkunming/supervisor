package com.unknown.supervisor.portal.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建用户VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "创建用户VO")
public class SysUserCreateInputVO {
    /**
     * 操作员号
     */
    @NotBlank(message = "操作员号不能为空")
    @Schema(description = "操作员号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String operNo;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @Schema(description = "用户类型（00系统用户）")
    private String userType;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Schema(description = "用户性别（0男 1女 2未知）")
    private String sex;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    /**
     * 账号状态（0正常 1停用）
     */
    @Schema(description = "账号状态（0正常 1停用）")
    private String status;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer orderNum;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}