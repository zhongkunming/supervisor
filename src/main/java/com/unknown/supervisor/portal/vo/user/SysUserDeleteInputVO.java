package com.unknown.supervisor.portal.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 删除用户VO
 *
 * @author zhongkunming
 */
@Data
public class SysUserDeleteInputVO {

    /**
     * 主键ID
     */
    @NotEmpty(message = "用户ID列表不能为空")
    @Schema(description = "用户ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> ids;
}