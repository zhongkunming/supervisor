package com.unknown.supervisor.portal.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统用户删除输入VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统用户删除输入信息")
public class SysUserDeleteInputVO {

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long id;
}