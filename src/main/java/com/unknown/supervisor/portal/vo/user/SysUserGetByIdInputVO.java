package com.unknown.supervisor.portal.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 根据ID查询用户输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "根据ID查询用户输入VO")
public class SysUserGetByIdInputVO {

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户ID不能为空")
    private Long id;
}