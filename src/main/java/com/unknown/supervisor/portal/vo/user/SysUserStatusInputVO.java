package com.unknown.supervisor.portal.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统用户状态输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统用户状态输入信息")
public class SysUserStatusInputVO {

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @Schema(description = "状态（1-启用，0-禁用）")
    @NotNull(message = "状态不能为空")
    private Integer status;
}